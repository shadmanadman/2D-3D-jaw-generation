package litert

import android.content.Context
import com.google.ai.edge.litert.Accelerator
import com.google.ai.edge.litert.CompiledModel
import com.google.ai.edge.litert.Model
import startup.AppContext

actual class Interpreter actual constructor(model: ByteArray) {
    private val context: Context by lazy { AppContext.get() }
    private val model = Model.load(model.writeToTempFile(context).path)
    private lateinit var compiledModel : CompiledModel

    actual fun init(){
        compiledModel = CompiledModel.create(model = model, options = CompiledModel.Options(Accelerator.GPU))
    }
    actual fun run(inputs: List<Any>, outputs: Map<Int, Any>) {
        val inputBuffers = compiledModel.createInputBuffers()
        val outputBuffers = compiledModel.createOutputBuffers()
        compiledModel.run(inputBuffers, outputBuffers)
    }

    actual fun close() {
        compiledModel.close()
    }

}