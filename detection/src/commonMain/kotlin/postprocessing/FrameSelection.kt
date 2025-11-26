package postprocessing

import model.AcceptedFrame
import model.JawSide


data class FrameScore(
    val frame: AcceptedFrame,
    val averageClarity: Double,
    val maxConfidence: Double,
    val coverage: Double
)


fun List<AcceptedFrame>.selectBestFrames(): List<AcceptedFrame> {
    val groupedFrames = this.groupBy { it.jawSide }

    val bestFrames = mutableListOf<AcceptedFrame>()

    groupedFrames.forEach { (section, framesInSection) ->
        // Calculate the average clarity for each frame and add tie-breaking parameters
        val framesWithScore = framesInSection.map { frame ->
            val averageClarity = frame.boxes.map { it.clarityLevel }.average()
            val maxConfidence = frame.boxes.map { it.maxConf }.average()
            val coverage = frame.boxes.sumOf { it.width.toDouble() * it.height.toDouble() }
            FrameScore(frame, averageClarity, maxConfidence, coverage)
        }

        val sortedFrames = framesWithScore.filter { it.frame.frame!=null }.sortedWith(compareByDescending<FrameScore> {
            it.averageClarity
        }.thenByDescending {
            it.maxConfidence // Tie-breaking by maxConf
        }.thenByDescending {
            it.coverage // Further tie-breaking by coverage
        }).map { it.frame }

        val selectedFrames = when (section) {
            JawSide.LEFT, JawSide.RIGHT -> sortedFrames.take(2) // Select top 2 frames for Left and Right sections
            JawSide.MIDDLE -> sortedFrames.take(1) // Select top 1 frame for Center section
        }

        bestFrames.addAll(selectedFrames)
    }

    return bestFrames
}
