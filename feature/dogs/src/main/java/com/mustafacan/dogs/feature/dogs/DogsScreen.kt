package com.mustafacan.dogs.feature.dogs

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mustafacan.core.model.enums.*
import com.mustafacan.core.components.emptyscreen.EmptyResultForApiRequest
import com.mustafacan.core.components.emptyscreen.EmptyResultForSearch
import com.mustafacan.core.components.loading.LoadingErrorScreen
import com.mustafacan.core.components.loading.LoadingScreen
import com.mustafacan.core.components.searchbar.LocalSearch
import com.mustafacan.core.components.searchbar.RemoteSearch
import com.mustafacan.core.components.toolbar.Toolbar
import com.mustafacan.core.components.toolbar.ToolbarAction
import com.mustafacan.core.navigation.root.NavDestinationItem
import com.mustafacan.core.util.rememberFlowWithLifecycle
import com.mustafacan.core.R
import com.mustafacan.core.components.allfavoriteanimals.ShowAllFavoriteAnimals
import com.mustafacan.core.components.image.ImageViewer
import com.mustafacan.core.components.settings.SettingsScreenWithBottomSheet
import com.mustafacan.core.components.settings.SettingsScreenWithPopup
import com.mustafacan.dogs.feature.dogs.list.DogList

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun DogsScreen(navController: NavController) {
    val viewModel = hiltViewModel<DogsViewModel>()
    val state = viewModel.state.collectAsStateWithLifecycle()
    val effect = rememberFlowWithLifecycle(viewModel.effect)

    val actionListForToolbar = listOf(ToolbarAction.Favorites(action = { viewModel.showAllFavoriteAnimals() }, state.value.allFavoriteAnimals.getCount()),
        ToolbarAction.OpenSettings(action = { viewModel.navigateToSettings() }))

    LaunchedEffect(Unit) { viewModel.loadSettings() }

    LaunchedEffect(effect) {
        effect.collect { action ->
            when (action) {
                is DogsScreenUiStateManager.DogsScreenEffect.NavigateToDogDetail -> {
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
        //toolbar
        Toolbar(title = "Animals App - Dogs", actionList = actionListForToolbar)

        //searchbar
        if (!state.value.dogsBackup.isNullOrEmpty()) {
            if (state.value.searchType == SearchType.LOCAL_SEARCH)
                LocalSearch { viewModel.localSearch(it) }
            else if (state.value.searchType == SearchType.REMOTE_SEARCH)
                RemoteSearch { viewModel.remoteSearch(it) }
        }

        //dog list
        DogListContent(viewModel, state)
    }

    if (state.value.showAllFavoriteAnimalsPopup) {
        ShowAllFavoriteAnimals(allFavoriteAnimals = state.value.allFavoriteAnimals, onDismiss = { viewModel.closeAllFavoriteAnimals() })
    }

}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun DogListContent(viewModel: DogsViewModel, uiState: State<DogsScreenUiStateManager.DogsScreenState>) {

    if (uiState.value.loading) {
        LoadingScreen()
    } else if (uiState.value.errorMessage != null) {
        LoadingErrorScreen(
            text = uiState.value.errorMessage!!,
            retryOnClick = { viewModel.callDogs() },
            loadLocalDataClick = { viewModel.getDogsWithTemporaryData() })
    } else if (uiState.value.dogs != null && !uiState.value.dogs!!.isEmpty()) {
        DogList(
            uiState = uiState,
            clickedItem = { dog -> viewModel.navigateToDogDetail(dog) },
            addFavorite = { dog -> viewModel.addFavoriteDog(dog) },
            deleteFavorite = { dog -> viewModel.deleteFavoriteDog(dog) },
            showBigImage = { dog -> viewModel.showBigImage(dog) })
    } else if (uiState.value.dogsBackup.isNullOrEmpty()) {
        EmptyResultForApiRequest(
            text = stringResource(id = R.string.empty),
            retryOnClick = { viewModel.callDogs() })
    } else if (!uiState.value.dogsBackup.isNullOrEmpty() && uiState.value.dogs.isNullOrEmpty()) {
        EmptyResultForSearch()
    }

    if (uiState.value.showBigImage) {
        ImageViewer(
            imageUrl = uiState.value.selectedDogForBigImage?.image!!,
            uiState.value.selectedDogForBigImage?.name!!,
            uiState.value.selectedDogForBigImage?.temperament!!,
            uiState.value.selectedDogForBigImage?.isFavorite ?: false,
            onDismiss = {
                viewModel.closeBigImage()
            },
            updateFavorite = {
                if (!uiState.value.selectedDogForBigImage!!.isFavorite!!)
                    viewModel.addFavoriteDog(uiState.value.selectedDogForBigImage!!.copy(isFavorite = true))
                else
                    viewModel.deleteFavoriteDog(uiState.value.selectedDogForBigImage!!)
            })

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
                onDismiss = { viewModel.closeSettings() })
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
                onDismiss = { viewModel.closeSettings() })
        }

    }
}




