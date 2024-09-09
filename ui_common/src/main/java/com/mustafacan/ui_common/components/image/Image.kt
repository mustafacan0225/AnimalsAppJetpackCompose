package com.mustafacan.ui_common.components.image

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.mustafacan.ui_common.R

@Composable
fun CircleImage(url: String) {
    AsyncImage(modifier = Modifier
        .size(80.dp)
        .clip(CircleShape),
        model = url,
        placeholder = painterResource(id = R.drawable.loadingimage),
        error = painterResource(id = R.drawable.loadingimage),
        contentDescription = "",
        alignment = Alignment.Center,
        contentScale = ContentScale.FillBounds
    )
}