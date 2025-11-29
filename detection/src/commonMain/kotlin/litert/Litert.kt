package litert

object Litert {
    private var interpreter: Interpreter? = null

    fun init(model: ByteArray) {
        check(interpreter == null) { "Interpreter already initialized." }
        interpreter = Interpreter(model)
    }
}