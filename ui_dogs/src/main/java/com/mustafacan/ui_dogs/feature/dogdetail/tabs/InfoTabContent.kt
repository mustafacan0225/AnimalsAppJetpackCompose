package com.mustafacan.ui_dogs.feature.dogdetail.tabs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
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
import com.mustafacan.domain.model.dogs.Dog
import com.mustafacan.ui_dogs.R

@Composable
fun InfoTabContent(dog: Dog) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        androidx.compose.material3.Text(buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = colorResource(id = R.color.indicator_color), textDecoration = TextDecoration.Underline)) {
                appendLine(stringResource(id = R.string.name))
            }
            appendLine(dog.name)
        }, textAlign = TextAlign.Center)

        androidx.compose.material3.Text(buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = colorResource(id = R.color.indicator_color), textDecoration = TextDecoration.Underline)) {
                appendLine(stringResource(id = R.string.breed_group))
            }
            appendLine(dog.breedGroup)
        }, textAlign = TextAlign.Center)

        androidx.compose.material3.Text(buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = colorResource(id = R.color.indicator_color), textDecoration = TextDecoration.Underline)) {
                appendLine(stringResource(id = R.string.origin))
            }
            appendLine(dog.origin)
        }, textAlign = TextAlign.Center)

        androidx.compose.material3.Text(buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = colorResource(id = R.color.indicator_color), textDecoration = TextDecoration.Underline)) {
                appendLine(stringResource(id = R.string.lifespan))
            }
            appendLine(dog.lifespan)
        }, textAlign = TextAlign.Center)

        androidx.compose.material3.Text(buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = colorResource(id = R.color.indicator_color), textDecoration = TextDecoration.Underline)) {
                appendLine(stringResource(id = R.string.size))
            }
            appendLine(dog.size)
        }, textAlign = TextAlign.Center)
    }
}