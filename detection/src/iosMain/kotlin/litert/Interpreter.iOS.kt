package litert

import cocoapods.TFLTensorFlowLite.TFLInterpreter
import cocoapods.TFLTensorFlowLite.TFLInterpreterOptions
import cocoapods.TFLTensorFlowLite.TFLMetalDelegate
import cocoapods.TFLTensorFlowLite.TFLMetalDelegateOptions
import cocoapods.TFLTensorFlowLite.TFLMetalDelegateThreadWaitType
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSData

actual class Interpreter actual constructor(val model: ByteArray) {

    @OptIn(ExperimentalForeignApi::class)
    private var tflInterpreter: TFLInterpreter? = null

    @OptIn(ExperimentalForeignApi::class)
    actual fun init(){
        val options = TFLInterpreterOptions().apply {
            setNumberOfThreads(4)
            setMetalDelegation()
            }

        tflInterpreter = errorHandled { errPtr ->
            TFLInterpreter(model.writeToTempFile(),options, errPtr)
        }!!

        errorHandled { errPtr ->
            val interpreter = requireNotNull(tflInterpreter) { "Interpreter has been closed or not initialized." }
            interpreter.allocateTensorsWithError(errPtr)
        }
    }
    @OptIn(ExperimentalForeignApi::class)
    actual fun run(inputs: List<Any>, outputs: Map<Int, Any>) {
        if (inputs.size > getInputTensorCount()) throw IllegalArgumentException("Wrong inputs dimension.")
        val interpreter = requireNotNull(tflInterpreter) { "Interpreter has been closed or not initialized." }


        println("Input size: ${(inputs[0] as NSData).length}")
        inputs.forEachIndexed { index, input ->
            val inputTensor = getInputTensor(index)
            println("inputTensor: $input")
            errorHandled { errPtr ->
                inputTensor.platformTensor.copyData(
                    input as NSData,
                    errPtr
                )
            }
        }

        // Run inference
        errorHandled { errPtr ->
            interpreter.invokeWithError(errPtr)
        }

    }


    /**
     * TFLInterpreter does not require explicit .close() in Objective-C/Swift,
     * releasing the reference is sufficient for ARC to clean it up
     */
    @OptIn(ExperimentalForeignApi::class)
    actual fun close() {
        tflInterpreter = null
    }

    fun getInputTensorCount(): Int {
        val interpreter = requireNotNull(tflInterpreter) { "Interpreter has been closed or not initialized." }
        return interpreter.inputTensorCount().toInt()
    }

    /**
     * Gets the number of output Tensors.
     */
    fun getOutputTensorCount(): Int {
        val interpreter = requireNotNull(tflInterpreter) { "Interpreter has been closed or not initialized." }
        return interpreter.outputTensorCount().toInt()
    }

    /**
     * Gets the Tensor associated with the provdied input index.
     *
     * @throws IllegalArgumentException if [index] is negative or is not smaller than the
     * number of model inputs.
     */
    fun getInputTensor(index: Int): Tensor {
        val interpreter = requireNotNull(tflInterpreter) { "Interpreter has been closed or not initialized." }
        return errorHandled { errPtr ->
            interpreter.inputTensorAtIndex(index.toULong(), errPtr)
        }!!.toTensor()
    }

    /**
     * Gets the Tensor associated with the provdied output index.
     *
     * @throws IllegalArgumentException if [index] is negative or is not smaller than the
     * number of model inputs.
     */
    fun getOutputTensor(index: Int): Tensor {
        val interpreter = requireNotNull(tflInterpreter) { "Interpreter has been closed or not initialized." }
        return errorHandled { errPtr ->
            interpreter.outputTensorAtIndex(index.toULong(), errPtr)
        }!!.toTensor()
    }
}


@OptIn(ExperimentalForeignApi::class)
private fun setMetalDelegation() = TFLMetalDelegateOptions().apply {
    precisionLossAllowed = false
    quantizationEnabled = false
    waitType = TFLMetalDelegateThreadWaitType.TFLMetalDelegateThreadWaitTypeAggressive
    TFLMetalDelegate(options = this)
}