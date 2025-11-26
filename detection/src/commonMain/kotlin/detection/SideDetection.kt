package detection

import model.JawSide
import model.JawType
import model.ToothNumber

private fun findCurrentSide(
    leftSideTeeth: Set<ToothNumber>,
    midSideTeeth: Set<ToothNumber>,
    rightSideTeeth: Set<ToothNumber>,
    result: Set<ToothNumber>
): JawSide {
    val leftIntersectCount = leftSideTeeth.intersect(result).size
    val midIntersectCount = midSideTeeth.intersect(result).size
    val rightIntersectCount = rightSideTeeth.intersect(result).size

    val intersectsCounts = listOf(leftIntersectCount, midIntersectCount, rightIntersectCount)
    val max = intersectsCounts.maxOrNull()
    val maxIndexes = intersectsCounts.indices.filter { intersectsCounts[it] == max }

    return if (maxIndexes.size > 1) {
        JawSide.MIDDLE
    } else {
        when (maxIndexes.firstOrNull()) {
            0 -> JawSide.LEFT
            1 -> JawSide.MIDDLE
            2 -> JawSide.RIGHT
            else -> JawSide.MIDDLE
        }
    }
}



private fun detectCurrentSide(
    visibleSet: Set<String?>,
    missing: List<String>,
    jawType: JawType
): JawSide {
    val result = (visibleSet.filterNotNull().toSet() + missing).map {
        it.toToothNumber()
    }.toSet()

    return when (jawType) {
        JawType.UPPER -> {
            val leftSideTeeth = setOf(
                ToothNumber.UL8,
                ToothNumber.UL7,
                ToothNumber.UL6,
                ToothNumber.UL5,
                ToothNumber.UL4
            )
            val midSideTeeth = setOf(
                ToothNumber.UL3,
                ToothNumber.UL2,
                ToothNumber.UL1,
                ToothNumber.UR1,
                ToothNumber.UR2,
                ToothNumber.UR3
            )
            val rightSideTeeth = setOf(
                ToothNumber.UR8,
                ToothNumber.UR7,
                ToothNumber.UR6,
                ToothNumber.UR5,
                ToothNumber.UR4
            )
            findCurrentSide(leftSideTeeth, midSideTeeth, rightSideTeeth, result)
        }

        JawType.LOWER -> {
            val leftSideTeeth = setOf(
                ToothNumber.LL8,
                ToothNumber.LL7,
                ToothNumber.LL6,
                ToothNumber.LL5,
                ToothNumber.LL4
            )
            val midSideTeeth = setOf(
                ToothNumber.LL3,
                ToothNumber.LL2,
                ToothNumber.LL1,
                ToothNumber.LR1,
                ToothNumber.LR2,
                ToothNumber.LR3
            )
            val rightSideTeeth = setOf(
                ToothNumber.LR8,
                ToothNumber.LR7,
                ToothNumber.LR6,
                ToothNumber.LR5,
                ToothNumber.LR4
            )
            findCurrentSide(leftSideTeeth, midSideTeeth, rightSideTeeth, result)
        }

        else -> JawSide.MIDDLE
    }
}

