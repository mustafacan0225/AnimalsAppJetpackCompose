package com.mustafacan.dogs.feature.dogdetail.tabs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.mustafacan.dogs.R

@Composable
fun GeneralTabContent(description: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 15.dp, end = 15.dp, top = 10.dp, bottom = 10.dp)
            .verticalScroll(rememberScrollState()),
    ) {

        androidx.compose.material3.Text(buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = colorResource(id = R.color.indicator_color))) {
                append(stringResource(id = R.string.detail_page_desc))
            }
            append(" " + description)
        })

    }
}