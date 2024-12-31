package com.mustafacan.ui_cats.feature.cats

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
import com.mustafacan.ui_cats.feature.cats.list.CatList
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
fun CatsScreen(navController: NavController) {
    val viewModel = hiltViewModel<CatsViewModel>()
    val state = viewModel.state.collectAsStateWithLifecycle()
    val effect = rememberFlowWithLifecycle(viewModel.effect)

    val actionListForToolbar =
        listOf(ToolbarAction.OpenSettings(action = { viewModel.navigateToSettings() }))

    LaunchedEffect(Unit) { viewModel.loadSettings() }

    LaunchedEffect(effect) {
        effect.collect { action ->
            when (action) {
                is CatsScreenUiStateManager.CatsScreenEffect.NavigateToCatDetail -> {
                    navController.navigate(NavDestinationItem.CatDetailScreen(cat = action.cat))
                }
            }
        }
    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(0.dp)) {
        //toolbar
        Toolbar(title = "Animals App - Cats", actionList = actionListForToolbar)

        //searchbar
        if (!state.value.catsBackup.isNullOrEmpty()) {
            if (state.value.searchType == SearchType.LOCAL_SEARCH)
                LocalSearch { viewModel.localSearch(it) }
            else if (state.value.searchType == SearchType.REMOTE_SEARCH)
                RemoteSearch { viewModel.remoteSearch(it) }
        }

        //cat list
        CatListContent(viewModel, state)
    }

}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun CatListContent(viewModel: CatsViewModel, uiState: State<CatsScreenUiStateManager.CatsScreenState>) {

    if (uiState.value.loading) {
        LoadingScreen()
    } else if (uiState.value.errorMessage != null) {
        LoadingErrorScreen(
            text = uiState.value.errorMessage!!,
            retryOnClick = { viewModel.callCats() },
            loadLocalDataClick = { viewModel.getCatsWithTemporaryData() })
    } else if (uiState.value.cats != null && !uiState.value.cats!!.isEmpty()) {
        CatList(
            uiState = uiState,
            clickedItem = { cat -> viewModel.navigateToCatDetail(cat) },
            addFavorite = { cat -> viewModel.addFavoriteCat(cat) },
            deleteFavorite = { cat -> viewModel.deleteFavoriteCat(cat) },
            showBigImage = { cat -> viewModel.showBigImage(cat) })
    } else if (uiState.value.catsBackup.isNullOrEmpty()) {
        EmptyResultForApiRequest(
            text = stringResource(id = R.string.empty),
            retryOnClick = { viewModel.callCats() })
    } else if (!uiState.value.catsBackup.isNullOrEmpty() && uiState.value.cats.isNullOrEmpty()) {
        EmptyResultForSearch()
    }

    if (uiState.value.showSettings) {
        if (uiState.value.viewTypeForSettings == ViewTypeForSettings.POPUP) {
            SettingsScreenWithPopup(uiState.value.viewTypeForList,
                uiState.value.viewTypeForSettings,
                uiState.value.searchType,
                saveSettings = { viewTypeCats, viewTypeSettings, searchType ->
                    viewModel.settingsUpdated(
                        viewTypeCats,
                        viewTypeSettings,
                        searchType
                    )
                },
                onDismiss = { viewModel.closeSettings() })
        } else if (uiState.value.viewTypeForSettings == ViewTypeForSettings.BOTTOM_SHEET) {
            SettingsScreenWithBottomSheet(uiState.value.viewTypeForList,
                uiState.value.viewTypeForSettings,
                uiState.value.searchType,
                saveSettings = { viewTypeCats, viewTypeSettings, searchType ->
                    viewModel.settingsUpdated(
                        viewTypeCats,
                        viewTypeSettings,
                        searchType
                    )
                },
                onDismiss = { viewModel.closeSettings() })
        }

    }

    if (uiState.value.showBigImage) {
        ImageViewer(
            imageUrl = uiState.value.selectedCatForBigImage?.image!!,
            uiState.value.selectedCatForBigImage?.name!!,
            uiState.value.selectedCatForBigImage?.temperament ?: "",
            uiState.value.selectedCatForBigImage?.isFavorite ?: false,
            onDismiss = {
                viewModel.closeBigImage()
            },
            updateFavorite = {
                if (!uiState.value.selectedCatForBigImage!!.isFavorite!!)
                    viewModel.addFavoriteCat(
                        uiState.value.selectedCatForBigImage!!.copy(
                            isFavorite = true
                        )
                    )
                else
                    viewModel.deleteFavoriteCat(uiState.value.selectedCatForBigImage!!)
            })

    }
}