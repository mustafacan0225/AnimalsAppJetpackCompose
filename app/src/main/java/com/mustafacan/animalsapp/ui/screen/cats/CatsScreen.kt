package com.mustafacan.animalsapp.ui.screen.cats

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mustafacan.animalsapp.ui.toolbar.Toolbar
import com.mustafacan.animalsapp.ui.toolbar.ToolbarAction

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatsScreen() {
    val viewModel: CatsViewModel = hiltViewModel()
    val actionList = listOf(ToolbarAction.Notifications(action = { Log.d("action", "Clicked Notifications") }),
        ToolbarAction.OpenSettings(action = { Log.d("action", "Clicked Settings") }))
    Column(
        Modifier
            .fillMaxWidth()
            .padding(0.dp)) {
        Toolbar(title = "Animals App - Cats", actionList = actionList)

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
        }
    }

}