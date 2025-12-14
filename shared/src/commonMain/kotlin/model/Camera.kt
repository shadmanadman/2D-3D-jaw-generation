package model

enum class FocusSection {
    NEAR,
    MIDDLE,
    FARTHEST}

enum class CameraErrorState {
    MoveAway,
    MoveMuchAway,
    MoveCloser,
    MoveMuchCloser,
    BadAngel,
    Ok
}

data class FocusPoints(val meteringPoint: MeteringPoint, val focusSection: FocusSection)
data class MeteringPoint(val x: Float, val y: Float,val size: Float = 3f)
