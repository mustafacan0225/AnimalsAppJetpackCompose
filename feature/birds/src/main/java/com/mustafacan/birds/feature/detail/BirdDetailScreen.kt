package com.mustafacan.birds.feature.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.mustafacan.domain.model.birds.Bird
import com.mustafacan.birds.R
import com.mustafacan.ui_common.components.image.CircleImage
import com.mustafacan.ui_common.components.image.ImageViewer
import com.mustafacan.ui_common.components.lottie.LikeAnimation
import com.mustafacan.ui_common.components.toolbar.Toolbar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BirdDetailScreen(navController: NavController, viewModel: BirdDetailViewModel) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = viewModel.bird!!.id) {
        viewModel.load()
    }

    Column(
        Modifier.fillMaxWidth()
    ) {

        Toolbar(onBackPressed = { navController.popBackStack() })

        if (state.value.initialLoaded) {
            BirdImage(
                updateIsFavorite = {
                    viewModel.updateIsFavorite()
                },
                bird = state.value.bird ?: viewModel.bird,
                isSelectedFavIcon = state.value.isSelectedFavIcon,
                showBigImage = { viewModel.showBigImage() }
            )

            BirdDetailContent(bird = state.value.bird)

            if (state.value.showBigImage) {
                ImageViewer(
                    imageUrl = (state.value.bird?.image ?: viewModel.bird?.image) ?: "",
                    (state.value.bird?.name ?: viewModel.bird?.name) ?: "",
                    (state.value.bird?.habitat ?: viewModel.bird?.habitat)
                        ?: "" + ", " + (state.value.bird?.placeOfFound
                            ?: viewModel.bird?.placeOfFound),
                    isSelectedFavIcon = state.value.isSelectedFavIcon,
                    onDismiss = { viewModel.closeBigImage() },
                    updateFavorite = { viewModel.updateIsFavorite() }
                )

            }
        }


    }
}

@Composable
fun BirdDetailContent(bird: Bird?) {
    bird?.let { b ->
        Column(
            modifier = Modifier
                .padding(20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.Bold,
                        color = colorResource(id = R.color.indicator_color)
                    )
                ) {
                    append(stringResource(id = R.string.detail_page_desc))
                }
                append(" " + b.description ?: "")
            })
            Spacer(modifier = Modifier.height(10.dp))
            Text(buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.Bold,
                        color = colorResource(id = R.color.indicator_color)
                    )
                ) {
                    append(stringResource(id = R.string.detail_page_species))
                }
                append(" " + b.species ?: "")
            })
            Spacer(modifier = Modifier.height(10.dp))
            Text(buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.Bold,
                        color = colorResource(id = R.color.indicator_color)
                    )
                ) {
                    append(stringResource(id = R.string.detail_page_family))
                }
                append(" " + b.family ?: "")
            })
            Spacer(modifier = Modifier.height(10.dp))
            Text(buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.Bold,
                        color = colorResource(id = R.color.indicator_color)
                    )
                ) {
                    append(stringResource(id = R.string.detail_page_habitat))
                }
                append(" " + b.habitat ?: "")
            })
            Spacer(modifier = Modifier.height(10.dp))
            Text(buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.Bold,
                        color = colorResource(id = R.color.indicator_color)
                    )
                ) {
                    append(stringResource(id = R.string.detail_page_place_of_found))
                }
                append(" " + b.placeOfFound ?: "")
            })
            Spacer(modifier = Modifier.height(10.dp))
            Text(buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.Bold,
                        color = colorResource(id = R.color.indicator_color)
                    )
                ) {
                    append(stringResource(id = R.string.detail_page_diet))
                }
                append(" " + b.diet ?: "")
            })
            Spacer(modifier = Modifier.height(10.dp))
            Text(buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.Bold,
                        color = colorResource(id = R.color.indicator_color)
                    )
                ) {
                    append(stringResource(id = R.string.detail_page_wingspan))
                }
                append(" " + b.wingspanCm.toString())
            })
            Spacer(modifier = Modifier.height(10.dp))
            Text(buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.Bold,
                        color = colorResource(id = R.color.indicator_color)
                    )
                ) {
                    append(stringResource(id = R.string.weight))
                }
                append(" " + b.weightKg.toString())
            })


        }
    }
}

@Composable
fun BirdImage(updateIsFavorite: () -> Unit, bird: Bird, isSelectedFavIcon: Boolean, showBigImage: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = colorResource(id = R.color.statusbar_color)),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            val (image, favoriteIcon, animationIcon, birdName) = createRefs()

            CircleImage(
                url = bird.image
                    ?: "https://cdn.pixabay.com/photo/2020/02/21/23/09/oriental-pied-hornbill-4868981_1280.jpg",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .constrainAs(image) {
                        centerHorizontallyTo(parent)
                    }.clickable { showBigImage() }
            )

            Icon(imageVector = if (isSelectedFavIcon) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = "favorite",
                tint = if (isSelectedFavIcon) Color.Red else Color.White,
                modifier = Modifier
                    .size(50.dp)
                    .constrainAs(favoriteIcon) {
                        centerHorizontallyTo(parent)
                        top.linkTo(image.bottom)
                        bottom.linkTo(image.bottom)
                    }
                    .clickable {
                        updateIsFavorite()
                    }

            )

            if (isSelectedFavIcon) {
                LikeAnimation(modifier = Modifier
                    .size(50.dp)
                    .constrainAs(animationIcon) {
                        centerHorizontallyTo(parent)
                        top.linkTo(image.bottom)
                        bottom.linkTo(image.bottom)
                    })
            }

            Text(
                text = bird.name ?: "",
                fontSize = 16.sp,
                color = Color.White,
                modifier = Modifier.constrainAs(birdName) {
                    centerHorizontallyTo(parent)
                    top.linkTo(favoriteIcon.bottom, 10.dp)
                    bottom.linkTo(parent.bottom, 10.dp)
                })

        }

    }
}