package com.mustafacan.ui_common.components.loading

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
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
import com.mustafacan.ui_common.R
import com.mustafacan.ui_common.components.lottie.LottieAnimationEmpty

@Composable
fun LoadingErrorScreen(text: String, retryOnClick: () -> Unit, loadLocalDataClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        LottieAnimationEmpty(modifier = Modifier.size(200.dp))
        /*Text(
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
        }*/

        //Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = stringResource(id = R.string.do_you_want_to_load_local_data),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(5.dp))
        Button(
            onClick = { retryOnClick() },
            colors = ButtonColors(containerColor = colorResource(id = R.color.indicator_color),
                contentColor = Color.White,
                disabledContentColor = Color.White,
                disabledContainerColor = Color.White)
        ) {
            Text(text = stringResource(id = R.string.retry_api_data), textAlign = TextAlign.Center)
        }
        Spacer(modifier = Modifier.height(5.dp))
        Button(
            onClick = { loadLocalDataClick() },
            colors = ButtonColors(containerColor = colorResource(id = R.color.indicator_color),
                contentColor = Color.White,
                disabledContentColor = Color.White,
                disabledContainerColor = Color.White)
        ) {
            Text(
                text = stringResource(id = R.string.retry_local_data),
                textAlign = TextAlign.Center
            )
        }

    }
}