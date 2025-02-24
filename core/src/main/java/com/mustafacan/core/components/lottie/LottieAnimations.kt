package com.mustafacan.core.components.lottie

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.mustafacan.core.R

@Composable
fun LottieAnimationForSearch(modifier: Modifier) {

    val rawComposition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.notfound))
    val progress by animateLottieCompositionAsState(
        composition = rawComposition,
        iterations = LottieConstants.IterateForever
    )

    LottieAnimation(
        composition = rawComposition,
        progress = { progress },
        modifier = modifier
    )
}

@Composable
fun LottieAnimationEmpty(modifier: Modifier) {

    val rawComposition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.empty))
    val progress by animateLottieCompositionAsState(
        composition = rawComposition,
        iterations = LottieConstants.IterateForever
    )

    LottieAnimation(
        composition = rawComposition,
        progress = { progress },
        modifier = modifier
    )
}

@Composable
fun LikeAnimation(modifier: Modifier) {

    val rawComposition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.like))
    val progress by animateLottieCompositionAsState(
        composition = rawComposition,
        iterations = LottieConstants.IterateForever
    )

    LottieAnimation(
        composition = rawComposition,
        progress = { progress },
        modifier = modifier
    )
}