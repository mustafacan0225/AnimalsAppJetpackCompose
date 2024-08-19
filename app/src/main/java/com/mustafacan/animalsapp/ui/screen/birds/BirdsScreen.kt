package com.mustafacan.animalsapp.ui.screen.birds

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mustafacan.animalsapp.ui.components.shimmer.sampleShimmerLayouts
import com.mustafacan.animalsapp.ui.components.toolbar.Toolbar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BirdsScreen() {
    val viewModel: BirdsViewModel = hiltViewModel()

    Column(
        Modifier
            .fillMaxWidth()
            .padding(0.dp)) {

        Toolbar(title = "Animals App - Birds")

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .padding(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                "Birds Screen",
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 20.dp),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )

            Text(
                "Birds Screen Content",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 20.dp),
                textAlign = TextAlign.Center
            )

            sampleShimmerLayouts()
        }
    }

}