package com.mustafacan.ui_common.components.image

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.mustafacan.ui_common.R
import com.mustafacan.ui_common.components.lottie.LikeAnimation

@Composable
fun ImageViewer(
    imageUrl: String,
    name: String,
    infoText: String,
    isSelectedFavIcon: Boolean,
    onDismiss: () -> Unit,
    updateFavorite: () -> Unit,
) {
    Dialog(onDismissRequest = { onDismiss() }) {
        Surface(
            shape = RoundedCornerShape(25.dp),
            color = Color.Black,
            border = BorderStroke(1.dp, colorResource(id = R.color.indicator_color))
        ) {

            ConstraintLayout(
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight()

            ) {
                val (infoLayout, favoriteIcon, animationIcon) = createRefs()
                ShowImage(imageUrl)

                Box(modifier = Modifier
                    .background(Color.Black.copy(alpha = 0.5f))
                    .constrainAs(infoLayout) {
                        width = Dimension.fillToConstraints
                        height = Dimension.wrapContent
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    }) {

                    Text(
                        buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            ) {
                                appendLine(name)
                            }
                            withStyle(style = SpanStyle(color = Color.White)) {
                                append(infoText)
                            }

                        },
                        modifier = Modifier.padding(
                            top = 25.dp,
                            start = 15.dp,
                            end = 15.dp,
                            bottom = 15.dp
                        )
                    )
                }

                Icon(imageVector = if (isSelectedFavIcon) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "favorite",
                    tint = if (isSelectedFavIcon) Color.Red else Color.White,
                    modifier = Modifier
                        .size(50.dp)
                        .constrainAs(favoriteIcon) {
                            centerHorizontallyTo(parent)
                            top.linkTo(infoLayout.top)
                            bottom.linkTo(infoLayout.top)
                        }
                        .clickable {
                            updateFavorite()
                        }
                )

                if (isSelectedFavIcon) {
                    LikeAnimation(modifier = Modifier
                        .size(50.dp)
                        .constrainAs(animationIcon) {
                            centerHorizontallyTo(parent)
                            top.linkTo(infoLayout.top)
                            bottom.linkTo(infoLayout.top)
                        })
                }


            }
        }
    }

}