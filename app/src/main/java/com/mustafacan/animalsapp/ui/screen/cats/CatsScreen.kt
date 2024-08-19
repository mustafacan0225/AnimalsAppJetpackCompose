package com.mustafacan.animalsapp.ui.screen.cats

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mustafacan.animalsapp.ui.components.toolbar.Toolbar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatsScreen() {
    val viewModel: CatsViewModel = hiltViewModel()
    var counter = viewModel.localDataSource.getTestFlow().collectAsState(initial = 0)
    Column(
        Modifier
            .fillMaxWidth()
            .padding(0.dp)) {
        Toolbar(title = "Animals App - Cats")

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                "Cats Screen",
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 20.dp),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )

            Text(
                "Cats Screen Content",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 20.dp),
                textAlign = TextAlign.Center
            )

            Row(horizontalArrangement = Arrangement.Center) {
                Button(onClick = { viewModel.incrementCounter() }) {
                    Text(text = "Increment")
                }
                Spacer(modifier = Modifier.width(16.dp))
                Button(onClick = { viewModel.decrementCounter() }) {
                    Text(text = "Decrement")
                }
            }

            Text(
                "Result: ${counter.value}",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 20.dp),
                textAlign = TextAlign.Center
            )
        }
    }

}