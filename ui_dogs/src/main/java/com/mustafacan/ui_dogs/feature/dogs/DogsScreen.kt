package com.mustafacan.ui_dogs.feature.dogs

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
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
import com.mustafacan.ui_common.components.lottie.LikeAnimation
import com.mustafacan.ui_dogs.feature.settings.SettingsScreenWithBottomSheet
import com.mustafacan.ui_dogs.feature.settings.SettingsScreenWithPopup

@SuppressLint("CoroutineCreationDuringComposition")
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
                }, addFavorite = { dog ->
                    viewModel.addFavoriteDog(dog.copy(isFavorite = true))
                }, deleteFavorite = { dog ->
                    viewModel.deleteFavoriteDog(dog)
                })
        } else if (uiState.value.viewTypeForList == ViewTypeForList.LAZY_COLUMN) {
            DogListForLazyColumn(dogList = uiState.value.dogs!!,
                clickedItem = { dog ->
                    println("clicked item ${dog.name}")
                    viewModel.navigateToDogDetail(dog)
                }, addFavorite = { dog ->
                    viewModel.addFavoriteDog(dog.copy(isFavorite = true))
                }, deleteFavorite = { dog ->
                    viewModel.deleteFavoriteDog(dog)
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
fun DogListForLazyColumn(
    dogList: List<Dog>,
    clickedItem: (dog: Dog) -> Unit,
    addFavorite: (dog: Dog) -> Unit,
    deleteFavorite: (dog: Dog) -> Unit
) {

    //var list by remember { mutableStateOf(dogList) }
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
                border = BorderStroke(
                    1.dp,
                    if (dog.isFavorite ?: false) Color.Red else Color.White
                ),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {

                ConstraintLayout(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(10.dp)
                ) {
                    val (image, dogInfoLayaout, favoriteIcon) = createRefs()

                    Card(
                        modifier = Modifier
                            .width(80.dp)
                            .height(80.dp)
                            .constrainAs(image) {
                                centerVerticallyTo(parent)
                                start.linkTo(parent.start)
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                            },
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White,
                        )
                    ) {
                        CircleImage(
                            url = "https://cdn.pixabay.com/photo/2016/02/19/15/46/labrador-retriever-1210559_1280.jpg",
                            modifier = Modifier
                                .size(80.dp)
                                .clip(CircleShape)
                        )
                    }

                    Column(modifier = Modifier.constrainAs(dogInfoLayaout) {
                        centerVerticallyTo(parent)
                        start.linkTo(image.end, margin = 10.dp)
                        end.linkTo(favoriteIcon.start, margin = 10.dp)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        width = Dimension.fillToConstraints
                    }) {
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

                    IconButton(onClick = {
                        if (dog.isFavorite ?: false)
                            deleteFavorite(dog)
                        else
                            addFavorite(dog)
                    }, modifier = Modifier.constrainAs(favoriteIcon) {
                        centerVerticallyTo(parent)
                        end.linkTo(parent.end)
                    }) {
                        if (dog.isFavorite?: false) {
                            LikeAnimation(modifier = Modifier.size(50.dp))
                        } else {
                            Icon(
                                imageVector = Icons.Default.FavoriteBorder,
                                contentDescription = "favorite",
                                tint = Color.Black

                            )
                        }
                        /*Icon(
                            imageVector = if (dog.isFavorite
                                    ?: false
                            ) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "favorite",
                            tint = if (dog.isFavorite ?: false) Color.Red else Color.Black

                        )*/
                    }

                }

            }
        }
    }

}

@Composable
fun DogListForLazyVerticalGrid(
    dogList: List<Dog>,
    clickedItem: (dog: Dog) -> Unit,
    addFavorite: (dog: Dog) -> Unit,
    deleteFavorite: (dog: Dog) -> Unit
) {
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
                border = BorderStroke(
                    1.dp,
                    if (dog.isFavorite ?: false) Color.Red else Color.White
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
                            .padding(top = 10.dp, start = 10.dp, end = 10.dp)
                    )

                    Card(
                        modifier = Modifier
                            .width(80.dp)
                            .height(80.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White,
                        ),
                        shape = CircleShape
                    ) {
                        CircleImage(
                            url = "https://cdn.pixabay.com/photo/2016/02/19/15/46/labrador-retriever-1210559_1280.jpg",
                            modifier = Modifier
                                .size(80.dp)
                                .clip(CircleShape)
                        )
                    }
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 5.dp),
                        text = dog.origin ?: "",
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                    )

                    IconButton(onClick = {
                        if (dog.isFavorite ?: false)
                            deleteFavorite(dog)
                        else
                            addFavorite(dog)
                    }, modifier = Modifier.align(Alignment.CenterHorizontally)) {
                        if (dog.isFavorite?: false) {
                            LikeAnimation(modifier = Modifier.size(50.dp))
                        } else {
                            Icon(
                                imageVector = Icons.Default.FavoriteBorder,
                                contentDescription = "favorite",
                                tint = Color.Black

                            )
                        }
                        /*Icon(
                            imageVector = if (dog.isFavorite
                                    ?: false
                            ) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "favorite",
                            tint = if (dog.isFavorite ?: false) Color.Red else Color.Black
                        )*/
                    }
                }
            }
        }
    }

}


