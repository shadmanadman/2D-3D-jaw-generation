package camera.viewmodel

import androidx.lifecycle.ViewModel
import ext.convertToJawStatus
import kotlinx.coroutines.flow.MutableStateFlow
import model.FrameAnalyzeStatus
import model.JawSide
import model.JawSideStatus
import model.JawType
import kotlin.collections.set

class JawViewModel : ViewModel(){
    /**
     * save the status of analyzing for each side
     */
    private val _jawSideStatus = MutableStateFlow(
        mapOf(
            JawSideStatus.LOWER_LEFT to FrameAnalyzeStatus.None,
            JawSideStatus.LOWER_RIGHT to FrameAnalyzeStatus.None,
            JawSideStatus.LOWER_MIDDLE to FrameAnalyzeStatus.None,
            JawSideStatus.UPPER_LEFT to FrameAnalyzeStatus.None,
            JawSideStatus.UPPER_RIGHT to FrameAnalyzeStatus.None,
            JawSideStatus.UPPER_MIDDLE to FrameAnalyzeStatus.None,
            JawSideStatus.FRONT to FrameAnalyzeStatus.None,
        )
    )

    fun jawSideAnalyzeStarted(currentJawSide: JawSideStatus) {
        println("Jaw side analyze started: $currentJawSide")
        _jawSideStatus.value = _jawSideStatus.value.toMutableMap().apply {
            this[currentJawSide] = FrameAnalyzeStatus.Started
        }
    }

    fun jawSideAnalyzeCompleted(currentJawSide: JawSideStatus) {
        println("Jaw side analyze completed: $currentJawSide")
        _jawSideStatus.value = _jawSideStatus.value.toMutableMap().apply {
            this[currentJawSide] = FrameAnalyzeStatus.Completed
        }
    }


    fun checkIfCurrentSideAnalyzeCompleted(jawSide: JawSide, jawType: JawType): Boolean {
        val jawSideStatus = convertToJawStatus(jawSide, jawType)
        return _jawSideStatus.value[jawSideStatus] == FrameAnalyzeStatus.Completed
    }
}