package com.mustafacan.ui_common.components.image

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.mustafacan.ui_common.R

@Composable
fun ImageViewer(imageUrl: String) {
    var state = remember {
        mutableStateOf(true)
    }
    if (state.value) {
        Dialog(onDismissRequest = { state.value = false }
        ) {
            Surface(
                shape = RoundedCornerShape(25.dp),
                color = Color.Black,
                border = BorderStroke(1.dp, colorResource(id = R.color.indicator_color))
            ) {
                ShowImage(imageUrl)
            }
        }
    }

}