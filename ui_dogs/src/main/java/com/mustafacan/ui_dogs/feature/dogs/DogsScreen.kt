package com.mustafacan.ui_dogs.feature.dogs

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mustafacan.ui_common.model.enums.*
import com.mustafacan.domain.model.dogs.Dog
import com.mustafacan.ui_common.components.emptyscreen.EmptyResultForApiRequest
import com.mustafacan.ui_common.components.emptyscreen.EmptyResultForSearch
import com.mustafacan.ui_common.components.image.CircleImage
import com.mustafacan.ui_common.components.loading.LoadingErrorScreen
import com.mustafacan.ui_common.components.loading.LoadingScreen
import com.mustafacan.ui_common.components.searchbar.LocalSearch
import com.mustafacan.ui_common.components.searchbar.RemoteSearch
import com.mustafacan.ui_common.components.toolbar.Toolbar
import com.mustafacan.ui_common.components.toolbar.ToolbarAction
import com.mustafacan.ui_common.navigation.root.NavDestinationItem
import com.mustafacan.ui_common.util.rememberFlowWithLifecycle
import com.mustafacan.ui_common.R
import com.mustafacan.ui_dogs.feature.settings.SettingsScreenWithBottomSheet
import com.mustafacan.ui_dogs.feature.settings.SettingsScreenWithPopup

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DogsScreen(navController: NavController) {
    val viewModel = hiltViewModel<DogsViewModel>()
    val state = viewModel.state.collectAsStateWithLifecycle()
    val effect = rememberFlowWithLifecycle(viewModel.effect)

    val actionListForToolbar =
        listOf(ToolbarAction.OpenSettings(action = { viewModel.navigateToSettings() }))

    LaunchedEffect(effect) {
        effect.collect { action ->
            when (action) {
                is DogsScreenReducer.DogsScreenEffect.NavigateToDogDetail -> {
                    println("action called ${action.dog.name}")
                    navController.navigate(NavDestinationItem.DogDetailScreen(dog = action.dog))
                }

            }

        }

    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(0.dp)
    ) {
        Toolbar(title = "Animals App - Dogs", actionList = actionListForToolbar)
        if (!state.value.dogsBackup.isNullOrEmpty()) {
            if (state.value.searchType == SearchType.LOCAL_SEARCH) {
                LocalSearch {
                    viewModel.localSearch(it)
                }
            } else if (state.value.searchType == SearchType.REMOTE_SEARCH) {
                RemoteSearch {
                    viewModel.remoteSearch(it)
                }
            }

        }
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
        if (uiState.value.viewTypeForList == ViewTypeForList.LAZY_VERTICAL_GRID) {
            DogListForLazyVerticalGrid(dogList = uiState.value.dogs!!,
                clickedItem = { dog ->
                    println("clicked item ${dog.name}")
                    viewModel.navigateToDogDetail(dog)
                })
        } else if (uiState.value.viewTypeForList == ViewTypeForList.LAZY_COLUMN) {
            DogListForLazyColumn(dogList = uiState.value.dogs!!,
                clickedItem = { dog ->
                    println("clicked item ${dog.name}")
                    viewModel.navigateToDogDetail(dog)
                })
        }
    } else if (uiState.value.dogsBackup.isNullOrEmpty()) {
        EmptyResultForApiRequest(text = stringResource(id = R.string.empty),
            retryOnClick = {
                viewModel.callDogs()
            })
    } else if (!uiState.value.dogsBackup.isNullOrEmpty() && uiState.value.dogs.isNullOrEmpty()) {
        EmptyResultForSearch()
    }

    if (uiState.value.showSettings) {
        if (uiState.value.viewTypeForSettings == ViewTypeForSettings.POPUP) {
            SettingsScreenWithPopup(uiState.value.viewTypeForList,
                uiState.value.viewTypeForSettings,
                uiState.value.searchType,
                saveSettings = { viewTypeDogs, viewTypeSettings, searchType ->
                    viewModel.settingsUpdated(
                        viewTypeDogs,
                        viewTypeSettings,
                        searchType
                    )
                },
                onDismiss = { viewModel.closeSettings() }
            )
        } else if (uiState.value.viewTypeForSettings == ViewTypeForSettings.BOTTOM_SHEET) {
            SettingsScreenWithBottomSheet(uiState.value.viewTypeForList,
                uiState.value.viewTypeForSettings,
                uiState.value.searchType,
                saveSettings = { viewTypeDogs, viewTypeSettings, searchType ->
                    viewModel.settingsUpdated(
                        viewTypeDogs,
                        viewTypeSettings,
                        searchType
                    )
                },
                onDismiss = { viewModel.closeSettings() }
            )
        }

    }
}

@Composable
fun DogListForLazyColumn(dogList: List<Dog>, clickedItem: (dog: Dog) -> Unit) {
    LazyColumn(
        Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        items(dogList) { dog ->
            Card(
                Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .clickable { clickedItem(dog) },
                colors = CardDefaults.cardColors(
                    containerColor = Color.White,
                ),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .background(Color.White),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Card(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)                       // clip to the circle shape
                            .border(2.dp, Color.Gray, CircleShape),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White,
                        )
                    ) {
                        CircleImage(url = "https://cdn.pixabay.com/photo/2016/02/19/15/46/labrador-retriever-1210559_1280.jpg")
                    }

                    Column(modifier = Modifier.padding(10.dp)) {
                        Text(
                            text = dog.name ?: "",
                            fontWeight = FontWeight.Black
                        )

                        Text(
                            modifier = Modifier.padding(top = 5.dp, bottom = 5.dp),
                            text = dog.origin ?: ""
                        )

                        Text(text = dog.temperament ?: "", maxLines = 1)
                    }
                }
            }
        }
    }

}

@Composable
fun DogListForLazyVerticalGrid(dogList: List<Dog>, clickedItem: (dog: Dog) -> Unit) {
    LazyVerticalGrid(
        modifier =
        Modifier
            .fillMaxSize()
            .padding(10.dp),
        columns = GridCells.Fixed(2)
    ) {
        items(dogList) { dog ->
            Card(
                Modifier
                    .padding(10.dp)
                    .clickable { clickedItem(dog) },
                colors = CardDefaults.cardColors(
                    containerColor = Color.White,
                ),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = dog.name ?: "",
                        fontWeight = FontWeight.Black,
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 5.dp, start = 5.dp, end = 5.dp)
                    )
                    // LoadCircleImageGlide(url = dog.image ?: "")
                    Card(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)                       // clip to the circle shape
                            .border(2.dp, Color.Gray, CircleShape),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White,
                        )
                    ) {
                        CircleImage(url = "https://cdn.pixabay.com/photo/2016/02/19/15/46/labrador-retriever-1210559_1280.jpg")
                    }
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                        text = dog.origin ?: "",
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                    )
                }
            }
        }
    }

}


