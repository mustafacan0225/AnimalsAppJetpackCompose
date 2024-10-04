package com.mustafacan.ui_cats.feature.catdetail.tabs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.mustafacan.domain.model.cats.Cat
import com.mustafacan.ui_cats.R

@Composable
fun TemperamentTabContent(cat: Cat) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        androidx.compose.material3.Text(buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = colorResource(id = R.color.indicator_color))) {
                appendLine(stringResource(id = R.string.temperament_desc))
            }
            appendLine()
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = colorResource(id = R.color.indicator_color))) {
                appendLine(stringResource(id = R.string.tab_temperament))
            }
            appendLine()
            cat.getTemperamentList().forEach {
                appendLine(it)
                appendLine()
            }

        }, textAlign = TextAlign.Center)

    }
}