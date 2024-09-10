package com.mustafacan.ui_dogs.feature.dogdetail

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mustafacan.ui_common.components.toolbar.Toolbar
import com.mustafacan.ui_common.components.toolbar.ToolbarAction

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DogDetailScreen(navController: NavController, viewModel: DogDetailViewModel) {
    println("dog id: " + viewModel.dog?.name)
    /* var counter = viewModel.localDataSource.getTestFlow().collectAsState(initial = 0)
    val actionList = listOf(ToolbarAction.Favorites(action = { Log.d("action", "Clicked Favorites") }, badge = counter))*/
    val actionList =
        listOf(ToolbarAction.OpenSettings(action = {  }))
    Column(
        Modifier
            .fillMaxWidth()
            .padding(0.dp)) {
        Toolbar(title = "Dog Detail Page", onBackPressed = { navController.popBackStack() }, actionList = actionList)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
            /*.border(
                border = BorderStroke(
                    1.dp, MaterialTheme.colorScheme.primary
                ), MaterialTheme.shapes.large
            )*/,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                "Dog Detail Screen Content",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 20.dp),
                textAlign = TextAlign.Center
            )

        }

    }
}
