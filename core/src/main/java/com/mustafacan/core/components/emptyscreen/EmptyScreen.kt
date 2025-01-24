package com.mustafacan.core.components.emptyscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mustafacan.core.R
import com.mustafacan.core.components.lottie.LottieAnimationEmpty
import com.mustafacan.core.components.lottie.LottieAnimationForSearch

@Composable
fun EmptyResultForApiRequest(text: String, retryOnClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        LottieAnimationEmpty(modifier = Modifier.size(200.dp))

        Text(
            text = text,
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = Color.Black
        )

        IconButton(onClick = { retryOnClick() }) {
            Icon(
                modifier = Modifier
                    .size(50.dp)
                    .align(alignment = Alignment.CenterHorizontally),
                imageVector = Icons.Default.Refresh,
                contentDescription = "retry",
                tint = colorResource(id = R.color.statusbar_color)
            )
        }


    }
}

@Composable
fun EmptyResultForSearch() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        LottieAnimationForSearch(modifier = Modifier.size(150.dp))

        Text(
            text = stringResource(id = R.string.empty),
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            textAlign = TextAlign.Center,
            color = Color.Black
        )
    }
}