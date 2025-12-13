package camera.controller

import platform.SharedImage

expect class CameraController(){
    var onImageAvailable: ((SharedImage?) -> Unit)?
    fun setTorchMode(mode: TorchMode)
    fun startSession()
    fun stopSession()
}

enum class ImageFormat(val extension: String, val mimeType: String) {
    JPEG("jpg", "image/jpeg"),
    PNG("png", "image/jpeg")
}

enum class CameraLens {
    FRONT,
    BACK
}

enum class TorchMode {
    ON,
    OFF,
    AUTO,
}