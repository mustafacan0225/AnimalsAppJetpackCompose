package com.mustafacan.birds.feature.birds

import androidx.compose.runtime.Immutable
import com.mustafacan.domain.model.AllFavoriteAnimals
import com.mustafacan.domain.model.birds.Bird
import com.mustafacan.ui_common.model.enums.SearchType
import com.mustafacan.ui_common.model.enums.ViewTypeForList
import com.mustafacan.ui_common.model.enums.ViewTypeForSettings
import com.mustafacan.ui_common.viewmodel.UiStateManager

class BirdsScreenUiStateManager() : UiStateManager<BirdsScreenUiStateManager.BirdsScreenState, BirdsScreenUiStateManager.BirdsScreenEvent, BirdsScreenUiStateManager.BirdsScreenEffect> {

    @Immutable
    sealed class BirdsScreenEvent : UiStateManager.ViewEvent {
        object Loading : BirdsScreenEvent()
        data class BirdDetail(val bird: Bird) : BirdsScreenEvent()
        data class UpdateBirdIsFollowed(val bird: Bird) : BirdsScreenEvent()
        object OpenSettings : BirdsScreenEvent()
        object CloseSettings : BirdsScreenEvent()
        data class DataReceived(val list: List<Bird>? = null, val errorMessage: String? = null) : BirdsScreenEvent()
        data class FavoriteBirdsChanged(val favoriteList: List<Bird>) : BirdsScreenEvent()
        data class DataReceivedWithSearch(val list: List<Bird>? = null, val errorMessage: String? = null) : BirdsScreenEvent()
        data class DataChanged(val list: List<Bird>? = null) : BirdsScreenEvent()
        data class SettingsUpdated(val viewTypeDogs: ViewTypeForList, val viewTypeForSettings: ViewTypeForSettings, val searchType: SearchType) : BirdsScreenEvent()
        data class LoadSettings(val searchType: String?, val settingsType: String?, val listType: String?) : BirdsScreenEvent()
        data class ShowBigImage(val bird: Bird) : BirdsScreenEvent()
        object CloseBigImage : BirdsScreenEvent()
        data class AllFavoriteAnimalsChanged(val allFavoriteAnimals: AllFavoriteAnimals) : BirdsScreenEvent()
        object ShowAllFavoriteAnimals : BirdsScreenEvent()
        object CloseAllFavoriteAnimals : BirdsScreenEvent()
    }

    @Immutable
    sealed class BirdsScreenEffect : UiStateManager.ViewEffect {
        data class NavigateToBirdDetail(val bird: Bird) : BirdsScreenEffect()
    }

    @Immutable
    data class BirdsScreenState(
        val loading: Boolean, val errorMessage: String?, val birds: List<Bird>?,
        val birdsBackup: List<Bird>?, val viewTypeForList: ViewTypeForList = ViewTypeForList.LAZY_COLUMN,
        val viewTypeForSettings: ViewTypeForSettings = ViewTypeForSettings.POPUP,
        val searchType: SearchType = SearchType.LOCAL_SEARCH, val showSettings: Boolean, val favoriteAnimalCount: Int,
        val showBigImage: Boolean = false,
        val selectedBirdForBigImage: Bird? = null,
        val allFavoriteAnimals: AllFavoriteAnimals = AllFavoriteAnimals(),
        val showAllFavoriteAnimalsPopup: Boolean = false
    ) : UiStateManager.ViewState {
        companion object {
            fun initial(): BirdsScreenState {
                return BirdsScreenState(loading = true, errorMessage = null, birds = null, birdsBackup = null, showSettings = false, favoriteAnimalCount = 0)
            }
        }
    }

    override fun handleEvent(
        previousState: BirdsScreenState,
        event: BirdsScreenEvent
    ): Pair<BirdsScreenState, BirdsScreenEffect?> {

        return when (event) {

            is BirdsScreenEvent.Loading -> {
                previousState.copy(
                    loading = true
                ) to null
            }

            is BirdsScreenEvent.BirdDetail -> {
                previousState.copy() to BirdsScreenEffect.NavigateToBirdDetail(event.bird)
            }

            is BirdsScreenEvent.DataReceived -> {
                previousState.copy(
                    loading = false,
                    errorMessage = event.errorMessage,
                    birds = event.list,
                    birdsBackup = event.list
                ) to null
            }

            is BirdsScreenEvent.DataReceivedWithSearch -> {
                previousState.copy(
                    loading = false,
                    errorMessage = event.errorMessage,
                    birds = event.list,
                ) to null
            }

            is BirdsScreenEvent.DataChanged -> {
                previousState.copy(
                    birds = event.list
                ) to null
            }

            is BirdsScreenEvent.SettingsUpdated -> {
                previousState.copy(
                    viewTypeForList = event.viewTypeDogs,
                    viewTypeForSettings = event.viewTypeForSettings,
                    searchType = event.searchType,
                    showSettings = false
                ) to null
            }

            is BirdsScreenEvent.OpenSettings -> {
                previousState.copy(
                    showSettings = true
                ) to null
            }

            is BirdsScreenEvent.CloseSettings -> {
                previousState.copy(
                    showSettings = false
                ) to null
            }

            is BirdsScreenEvent.UpdateBirdIsFollowed -> {
                previousState.birds?.find { it.id == event.bird.id }?.isFavorite = event.bird.isFavorite
                previousState.birdsBackup?.find { it.id == event.bird.id }?.isFavorite = event.bird.isFavorite
                previousState.copy(birdsBackup = previousState.birdsBackup, birds = previousState.birds) to null
            }

            is BirdsScreenEvent.FavoriteBirdsChanged -> {
                previousState.birds?.map { it.isFavorite = false }
                previousState.birdsBackup?.map { it.isFavorite = false }

                event.favoriteList?.forEach { favoriteAnimal ->
                    previousState.birds?.find { it.id == favoriteAnimal.id }?.isFavorite = true
                    previousState.birdsBackup?.find { it.id == favoriteAnimal.id }?.isFavorite = true
                }

                previousState.copy(birds = previousState.birds, birdsBackup = previousState.birdsBackup, favoriteAnimalCount = event.favoriteList.size) to null
            }

            is BirdsScreenEvent.LoadSettings -> {
                previousState.copy(
                    searchType = enumValueOf(event.searchType ?: SearchType.LOCAL_SEARCH.name) as SearchType,
                    viewTypeForList = enumValueOf(event.listType ?: ViewTypeForList.LAZY_COLUMN.name) as ViewTypeForList,
                    viewTypeForSettings = enumValueOf(event.settingsType ?: ViewTypeForSettings.POPUP.name) as ViewTypeForSettings
                ) to null
            }

            is BirdsScreenEvent.ShowBigImage -> {
                previousState.copy(showBigImage = true, selectedBirdForBigImage = event.bird) to null
            }

            is BirdsScreenEvent.CloseBigImage -> {
                previousState.copy(showBigImage = false) to null
            }

            is BirdsScreenEvent.AllFavoriteAnimalsChanged -> {
                previousState.copy(allFavoriteAnimals = event.allFavoriteAnimals) to null
            }

            BirdsScreenEvent.CloseAllFavoriteAnimals -> {
                previousState.copy(showAllFavoriteAnimalsPopup = false) to null
            }

            BirdsScreenEvent.ShowAllFavoriteAnimals -> {
                previousState.copy(showAllFavoriteAnimalsPopup = true) to null
            }
        }
    }

}