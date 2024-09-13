package com.mustafacan.ui_dogs.feature.dogdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mustafacan.ui_common.components.image.CircleImage
import com.mustafacan.ui_common.components.toolbar.Toolbar
import com.mustafacan.ui_dogs.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DogDetailScreen(navController: NavController, viewModel: DogDetailViewModel) {
    println("dog id: " + viewModel.dog?.name)
    /* var counter = viewModel.localDataSource.getTestFlow().collectAsState(initial = 0)
    val actionList = listOf(ToolbarAction.Favorites(action = { Log.d("action", "Clicked Favorites") }, badge = counter))*/
    /*val actionList =
        listOf(ToolbarAction.OpenSettings(action = {  }))*/
    Column(
        Modifier
            .fillMaxWidth()
            .padding(0.dp)) {
        Toolbar(onBackPressed = { navController.popBackStack() })

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = colorResource(id = R.color.statusbar_color)),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            CircleImage(url = "https://cdn.pixabay.com/photo/2016/02/19/15/46/labrador-retriever-1210559_1280.jpg", modifier = Modifier
                .size(150.dp)
                .clip(CircleShape))


        }

    }
}
