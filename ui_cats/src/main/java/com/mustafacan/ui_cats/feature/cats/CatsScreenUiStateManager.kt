package com.mustafacan.ui_cats.feature.cats

import androidx.compose.runtime.Immutable
import com.mustafacan.domain.model.cats.Cat
import com.mustafacan.ui_common.model.enums.SearchType
import com.mustafacan.ui_common.model.enums.ViewTypeForList
import com.mustafacan.ui_common.model.enums.ViewTypeForSettings
import com.mustafacan.ui_common.viewmodel.UiStateManager

class CatsScreenUiStateManager() : UiStateManager<CatsScreenUiStateManager.CatsScreenState, CatsScreenUiStateManager.CatsScreenEvent, CatsScreenUiStateManager.CatsScreenEffect> {

    @Immutable
    sealed class CatsScreenEvent : UiStateManager.ViewEvent {
        object Loading : CatsScreenEvent()
        data class CatDetail(val cat: Cat) : CatsScreenEvent()
        data class UpdateCatIsFollowed(val cat: Cat) : CatsScreenEvent()
        object OpenSettings : CatsScreenEvent()
        object CloseSettings : CatsScreenEvent()
        data class DataReceived(val list: List<Cat>? = null, val errorMessage: String? = null) : CatsScreenEvent()
        data class FavoriteAnimalCountChanged(val favoriteList: List<Cat>) : CatsScreenEvent()
        data class DataReceivedWithSearch(val list: List<Cat>? = null, val errorMessage: String? = null) : CatsScreenEvent()
        data class DataChanged(val list: List<Cat>? = null) : CatsScreenEvent()
        data class SettingsUpdated(val viewTypeCats: ViewTypeForList, val viewTypeForSettings: ViewTypeForSettings, val searchType: SearchType) : CatsScreenEvent()
        data class LoadSettings(val searchType: String?, val settingsType: String?, val listType: String?) : CatsScreenEvent()
        data class ShowBigImage(val cat: Cat) : CatsScreenEvent()
        object CloseBigImage : CatsScreenEvent()
    }

    @Immutable
    sealed class CatsScreenEffect : UiStateManager.ViewEffect {
        data class NavigateToCatDetail(val cat: Cat) : CatsScreenEffect()
    }

    @Immutable
    data class CatsScreenState(
        val loading: Boolean, val errorMessage: String?, val cats: List<Cat>?,
        val catsBackup: List<Cat>?, val viewTypeForList: ViewTypeForList = ViewTypeForList.LAZY_COLUMN,
        val viewTypeForSettings: ViewTypeForSettings = ViewTypeForSettings.POPUP,
        val searchType: SearchType = SearchType.LOCAL_SEARCH, val showSettings: Boolean, val favoriteAnimalCount: Int,
        val showBigImage: Boolean = false,
        val selectedCatForBigImage: Cat? = null,
    ) : UiStateManager.ViewState {
        companion object {
            fun initial(): CatsScreenState {
                return CatsScreenState(loading = true, errorMessage = null, cats = null, catsBackup = null, showSettings = false, favoriteAnimalCount = 0)
            }
        }
    }

    override fun handleEvent(
        previousState: CatsScreenState,
        event: CatsScreenEvent
    ): Pair<CatsScreenState, CatsScreenEffect?> {

        return when (event) {

            is CatsScreenEvent.Loading -> {
                previousState.copy(
                    loading = true
                ) to null
            }

            is CatsScreenEvent.CatDetail -> {
                previousState.copy() to CatsScreenEffect.NavigateToCatDetail(event.cat)
            }

            is CatsScreenEvent.DataReceived -> {
                previousState.copy(
                    loading = false,
                    errorMessage = event.errorMessage,
                    cats = event.list,
                    catsBackup = event.list
                ) to null
            }

            is CatsScreenEvent.DataReceivedWithSearch -> {
                previousState.copy(
                    loading = false,
                    errorMessage = event.errorMessage,
                    cats = event.list,
                ) to null
            }

            is CatsScreenEvent.DataChanged -> {
                previousState.copy(
                    cats = event.list
                ) to null
            }

            is CatsScreenEvent.SettingsUpdated -> {
                previousState.copy(
                    viewTypeForList = event.viewTypeCats,
                    viewTypeForSettings = event.viewTypeForSettings,
                    searchType = event.searchType,
                    showSettings = false
                ) to null
            }

            is CatsScreenEvent.OpenSettings -> {
                previousState.copy(
                    showSettings = true
                ) to null
            }

            is CatsScreenEvent.CloseSettings -> {
                previousState.copy(
                    showSettings = false
                ) to null
            }

            is CatsScreenEvent.UpdateCatIsFollowed -> {
                previousState.cats?.find { it.id == event.cat.id }?.isFavorite = event.cat.isFavorite
                previousState.catsBackup?.find { it.id == event.cat.id }?.isFavorite = event.cat.isFavorite
                previousState.copy(catsBackup = previousState.catsBackup, cats = previousState.cats) to null
            }

            is CatsScreenEvent.FavoriteAnimalCountChanged -> {
                previousState.cats?.map { it.isFavorite = false }
                previousState.catsBackup?.map { it.isFavorite = false }

                event.favoriteList?.forEach { favoriteAnimal ->
                    previousState.cats?.find { it.id == favoriteAnimal.id }?.isFavorite = true
                    previousState.catsBackup?.find { it.id == favoriteAnimal.id }?.isFavorite = true
                }

                previousState.copy(cats = previousState.cats, catsBackup = previousState.catsBackup, favoriteAnimalCount = event.favoriteList.size) to null
            }

            is CatsScreenEvent.LoadSettings -> {
                previousState.copy(
                    searchType = enumValueOf(event.searchType ?: SearchType.LOCAL_SEARCH.name) as SearchType,
                    viewTypeForList = enumValueOf(event.listType ?: ViewTypeForList.LAZY_COLUMN.name) as ViewTypeForList,
                    viewTypeForSettings = enumValueOf(event.settingsType ?: ViewTypeForSettings.POPUP.name) as ViewTypeForSettings
                ) to null
            }

            is CatsScreenEvent.ShowBigImage -> {
                previousState.copy(showBigImage = true, selectedCatForBigImage = event.cat) to null
            }

            is CatsScreenEvent.CloseBigImage -> {
                previousState.copy(showBigImage = false) to null
            }

        }
    }

}