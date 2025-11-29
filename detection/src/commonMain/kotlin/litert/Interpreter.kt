package litert

internal expect class Interpreter(model: ByteArray) {
    fun init()
    /**
     * Runs model inference if the model takes multiple inputs, or returns multiple outputs.
     */
    fun run(inputs: List<Any>, outputs: Map<Int, Any>)

    /**
     * Release resources associated with the [Interpreter].
     */
    fun close()
}