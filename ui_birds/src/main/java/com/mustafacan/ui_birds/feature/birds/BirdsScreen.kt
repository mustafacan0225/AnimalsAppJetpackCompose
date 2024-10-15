package com.mustafacan.ui_birds.feature.birds

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.mustafacan.ui_birds.feature.birds.list.BirdList
import com.mustafacan.ui_common.R
import com.mustafacan.ui_common.components.emptyscreen.EmptyResultForApiRequest
import com.mustafacan.ui_common.components.emptyscreen.EmptyResultForSearch
import com.mustafacan.ui_common.components.image.ImageViewer
import com.mustafacan.ui_common.components.loading.LoadingErrorScreen
import com.mustafacan.ui_common.components.loading.LoadingScreen
import com.mustafacan.ui_common.components.searchbar.LocalSearch
import com.mustafacan.ui_common.components.searchbar.RemoteSearch
import com.mustafacan.ui_common.components.settings.SettingsScreenWithBottomSheet
import com.mustafacan.ui_common.components.settings.SettingsScreenWithPopup
import com.mustafacan.ui_common.components.toolbar.Toolbar
import com.mustafacan.ui_common.components.toolbar.ToolbarAction
import com.mustafacan.ui_common.model.enums.SearchType
import com.mustafacan.ui_common.model.enums.ViewTypeForSettings
import com.mustafacan.ui_common.navigation.root.NavDestinationItem
import com.mustafacan.ui_common.util.rememberFlowWithLifecycle

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun BirdsScreen(navController: NavController) {
    val viewModel = hiltViewModel<BirdsViewModel>()
    val state = viewModel.state.collectAsStateWithLifecycle()
    val effect = rememberFlowWithLifecycle(viewModel.effect)

    val actionListForToolbar =
        listOf(ToolbarAction.OpenSettings(action = { viewModel.navigateToSettings() }))

    LaunchedEffect(Unit) { viewModel.loadSettings() }

    LaunchedEffect(effect) {
        effect.collect { action ->
            when (action) {
                is BirdsScreenReducer.BirdsScreenEffect.NavigateToBirdDetail -> {
                    navController.navigate(NavDestinationItem.BirdDetailScreen(bird = action.bird))
                }
            }
        }
    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(0.dp)) {
        //toolbar
        Toolbar(title = "Animals App - Birds", actionList = actionListForToolbar)

        //searchbar
        if (!state.value.birdsBackup.isNullOrEmpty()) {
            if (state.value.searchType == SearchType.LOCAL_SEARCH)
                LocalSearch { viewModel.localSearch(it) }
            else if (state.value.searchType == SearchType.REMOTE_SEARCH)
                RemoteSearch { viewModel.remoteSearch(it) }
        }

        //bird list
        BirdListContent(viewModel, state)
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun BirdListContent(
    viewModel: BirdsViewModel,
    uiState: State<BirdsScreenReducer.BirdsScreenState>
) {

    if (uiState.value.loading) {
        LoadingScreen()
    } else if (uiState.value.errorMessage != null) {
        LoadingErrorScreen(
            text = uiState.value.errorMessage!!,
            retryOnClick = { viewModel.callBirds() },
            loadLocalDataClick = { viewModel.getBirdsWithTempData() })
    } else if (uiState.value.birds != null && !uiState.value.birds!!.isEmpty()) {
        BirdList(
            uiState = uiState,
            clickedItem = { bird -> viewModel.navigateToBirdDetail(bird) },
            addFavorite = { bird -> viewModel.addFavoriteBird(bird) },
            deleteFavorite = { bird -> viewModel.deleteFavoriteBird(bird) },
            showBigImage = { bird -> viewModel.showBigImage(bird) })
    } else if (uiState.value.birdsBackup.isNullOrEmpty()) {
        EmptyResultForApiRequest(
            text = stringResource(id = R.string.empty),
            retryOnClick = { viewModel.callBirds() })
    } else if (!uiState.value.birdsBackup.isNullOrEmpty() && uiState.value.birds.isNullOrEmpty()) {
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

    if (uiState.value.showBigImage) {
        ImageViewer(
            imageUrl = uiState.value.selectedBirdForBigImage?.image!!,
            uiState.value.selectedBirdForBigImage?.name!!,
            uiState.value.selectedBirdForBigImage?.habitat + ", " + uiState.value.selectedBirdForBigImage?.placeOfFound,
            uiState.value.selectedBirdForBigImage?.isFavorite ?: false,
            onDismiss = {
                viewModel.closeBigImage()
            },
            updateFavorite = {
                if (!uiState.value.selectedBirdForBigImage!!.isFavorite!!)
                    viewModel.addFavoriteBird(
                        uiState.value.selectedBirdForBigImage!!.copy(
                            isFavorite = true
                        )
                    )
                else
                    viewModel.deleteFavoriteBird(uiState.value.selectedBirdForBigImage!!)
            })

    }
}