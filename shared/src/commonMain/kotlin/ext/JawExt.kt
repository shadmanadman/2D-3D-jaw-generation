package ext

import model.JawSide
import model.JawSideStatus
import model.JawType

fun convertToJawStatus(jawSide: JawSide, jawType: JawType): JawSideStatus {
    return when (jawType) {
        JawType.LOWER -> {
            when (jawSide) {
                JawSide.LEFT -> JawSideStatus.LOWER_LEFT
                JawSide.RIGHT -> JawSideStatus.LOWER_RIGHT
                else -> JawSideStatus.LOWER_MIDDLE
            }
        }
        JawType.UPPER -> {
            when (jawSide) {
                JawSide.LEFT -> JawSideStatus.UPPER_LEFT
                JawSide.RIGHT -> JawSideStatus.UPPER_RIGHT
                else -> JawSideStatus.UPPER_MIDDLE
            }
        }
        else -> JawSideStatus.FRONT
    }
}