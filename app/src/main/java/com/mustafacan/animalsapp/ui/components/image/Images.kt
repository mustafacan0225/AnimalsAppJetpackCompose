package com.mustafacan.animalsapp.ui.components.image

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.mustafacan.animalsapp.R


@Composable
fun LoadCircleImage(url: String) {
    AsyncImage(modifier = Modifier.size(80.dp)
        .clip(CircleShape),
        model = url,
        placeholder = painterResource(id = R.drawable.loading),
        error = painterResource(id = R.drawable.loading),
        contentDescription = "",
        alignment = Alignment.Center,
        contentScale = ContentScale.Crop
    )
}

@Composable
fun LoadImage(url: String) {
    AsyncImage(modifier = Modifier
        .size(80.dp),
        model = url,
        placeholder = painterResource(id = R.drawable.loading),
        error = painterResource(id = R.drawable.loading),
        contentDescription = "",
        alignment = Alignment.Center,
    )
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun LoadCircleImageGlide(url: String) {
    GlideImage(modifier = Modifier.size(80.dp)
        .clip(CircleShape),
        model = url,
        loading = placeholder(R.drawable.imgloading),
        contentDescription = "",
        alignment = Alignment.Center,
        contentScale = ContentScale.FillBounds
    )
}