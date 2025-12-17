package jaw.scene

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import jaw_generation.jaw.generated.resources.Res
import jaw_generation.jaw.generated.resources.ic_guide_line
import jaw_generation.jaw.generated.resources.ic_guide_line_vertical
import org.jetbrains.compose.resources.painterResource

@Composable
fun JawGuideLine(modifier: Modifier = Modifier) {


    val parentModifier = modifier
        .width(310.dp)
        .height(410.dp)
        .padding(start = 20.dp, bottom = 15.dp)


    ConstraintLayout(
        modifier = parentModifier
    ) {
        val (
            guidelineLayout,
            imageViewGuidLine,
            imageViewGuidLine2,
            imageViewGuidLine3,
            imageViewGuidLine4,
            upperTextView,
            lowerTextView,
            textViewJawLeft,
            textViewJawRight
        ) =
            createRefs()


        Text(
            text = "Right Side",
            modifier = Modifier.constrainAs(textViewJawRight) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            }
        )

        Text(
            text = "Left Side",
            modifier = Modifier
                .padding(start = 8.dp, end = 8.dp)
                .constrainAs(textViewJawLeft) {
                    end.linkTo(parent.end, margin = 26.dp)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
        )

        Image(
            painter = painterResource(Res.drawable.ic_guide_line),
            contentDescription = null,
            modifier = Modifier
                .width(0.dp)
                .height(4.dp)
                .constrainAs(imageViewGuidLine) {
                    start.linkTo(textViewJawRight.end, margin = 10.dp)
                    end.linkTo(textViewJawLeft.start)
                    top.linkTo(textViewJawRight.top)
                    bottom.linkTo(textViewJawRight.bottom)
                }
        )

        Image(
            painter = painterResource(Res.drawable.ic_guide_line_vertical),
            contentDescription = null,
            modifier = Modifier
                .width(5.dp)
                .height(110.dp)
                .constrainAs(imageViewGuidLine2) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end, margin = 15.dp)
                    top.linkTo(parent.top)
                }
        )

        Image(
            painter = painterResource(Res.drawable.ic_guide_line_vertical),
            contentDescription = null,
            modifier = Modifier
                .width(5.dp)
                .height(130.dp)
                .constrainAs(imageViewGuidLine3) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end, margin = 15.dp)
                    top.linkTo(imageViewGuidLine2.bottom, margin = 35.dp)
                }
        )

        Image(
            painter = painterResource(Res.drawable.ic_guide_line_vertical),
            contentDescription = null,
            modifier = Modifier
                .width(10.dp)
                .height(110.dp)
                .constrainAs(imageViewGuidLine4) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end, margin = 15.dp)
                    top.linkTo(imageViewGuidLine3.bottom, margin = 35.dp)
                }
        )

        Text(
            text = "Upper Jaw",
            modifier = Modifier.constrainAs(upperTextView) {
                start.linkTo(parent.start)
                end.linkTo(parent.end, margin = 10.dp)
                top.linkTo(parent.top, margin = 115.dp)
            }
        )

        Text(
            text = "Lower Jaw",
            modifier = Modifier.constrainAs(lowerTextView) {
                start.linkTo(parent.start)
                end.linkTo(parent.end, margin = 10.dp)
                bottom.linkTo(parent.bottom, margin = 110.dp)
            }
        )
    }
}