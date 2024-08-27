package com.mustafacan.animalsapp.ui.screen.dogs

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.compose.material3.Card
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mustafacan.animalsapp.R
import com.mustafacan.animalsapp.ui.components.emptyscreen.EmptyScreen
import com.mustafacan.animalsapp.ui.components.image.LoadCircleImage
import com.mustafacan.animalsapp.ui.components.image.LoadImage
import com.mustafacan.animalsapp.ui.components.loading.LoadingErrorScreen
import com.mustafacan.animalsapp.ui.components.loading.LoadingScreen
import com.mustafacan.animalsapp.ui.components.toolbar.Toolbar
import com.mustafacan.animalsapp.ui.components.toolbar.ToolbarAction
import com.mustafacan.animalsapp.ui.util.rememberFlowWithLifecycle
import com.mustafacan.domain.model.dogs.Dog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DogsScreen(navController: NavController) {
    val viewModel = hiltViewModel<DogsViewModel>()
    val state = viewModel.state.collectAsStateWithLifecycle()
    val effect = rememberFlowWithLifecycle(viewModel.effect)
    val actionListForToolbar = listOf(ToolbarAction.OpenSettings(action = { viewModel.sendEffect(DogsScreenReducer.DogsScreenEffect.NavigateToSettings) }))

    LaunchedEffect(effect) {
        effect.collect { action ->
            when(action) {
                is DogsScreenReducer.DogsScreenEffect.NavigateToSettings -> { Log.d("action", "clicked settings") }
                is DogsScreenReducer.DogsScreenEffect.NavigateToDogDetail -> {}
                else -> {}
            }

        }

    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(0.dp)
    ) {
        Toolbar(title = "Animals App - Dogs", actionList = actionListForToolbar)
        DogListContent(viewModel, state)
    }

    /*var counter = viewModel.localDataSource.getTestFlow().collectAsState(initial = 0)
    Column(
        Modifier
            .fillMaxWidth()
            .padding(0.dp)) {

        Toolbar(title = "Animals App - Dogs")

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
                "Dog Screen Content",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 20.dp),
                textAlign = TextAlign.Center
            )

            Button(modifier = Modifier
                .padding(10.dp, 5.dp, 10.dp, 5.dp)
                .align(alignment = Alignment.CenterHorizontally),
                onClick = { navController.navigate(NavDestinationItem.DogsDetail.route) },
                shape = MaterialTheme.shapes.large,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text(text = "Detail Page", color = Color.White)
            }

            Text(
                "Result: ${counter.value}",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 20.dp),
                textAlign = TextAlign.Center
            )
        }

    }*/

}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun DogListContent(viewModel: DogsViewModel, uiState: State<DogsScreenReducer.DogsScreenState>) {

    if (uiState.value.loading) {
        LoadingScreen()
    } else if (uiState.value.errorMessage != null) {
        LoadingErrorScreen(text = uiState.value.errorMessage!!, retryOnClick = {
            viewModel.callDogs()
        })
    } else if (uiState.value.dogs != null && !uiState.value.dogs!!.isEmpty()) {
        LazyColumn(
            Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            items(uiState.value.dogs!!) { dog ->
                DogItemForLazyColumn(dog = dog)
            }
        }
    } else {
        EmptyScreen(text = stringResource(id = R.string.empty),
            retryOnClick = {
                viewModel.callDogs()
            })
    }


}

@Composable
fun DogItemForLazyColumn(dog: Dog) {
    Card(
        Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .background(Color.White)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.padding(start = 10.dp)) {

                LoadCircleImage(url = dog.image?: "")

                //test
                //LoadCircleImage(url = "https://cdn.pixabay.com/photo/2016/02/19/15/46/labrador-retriever-1210559_1280.jpg")
            }

            Column(modifier = Modifier.padding(10.dp)) {
                Text(
                    text = dog.name ?: "",
                    fontWeight = FontWeight.Black
                )

                Text(modifier = Modifier.padding(top = 5.dp, bottom = 5.dp), text = dog.origin ?: "")

                Text(text = dog.temperament ?: "")
            }
        }



    }
}