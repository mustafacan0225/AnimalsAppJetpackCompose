package com.mustafacan.ui_dogs.feature.dogs

import androidx.compose.runtime.Immutable
import com.mustafacan.domain.model.dogs.Dog
import com.mustafacan.ui_common.model.enums.SearchType
import com.mustafacan.ui_common.model.enums.ViewTypeForList
import com.mustafacan.ui_common.model.enums.ViewTypeForSettings
import com.mustafacan.ui_common.viewmodel.UiStateManager

class DogsScreenUiStateManager() : UiStateManager<DogsScreenUiStateManager.DogsScreenState, DogsScreenUiStateManager.DogsScreenEvent, DogsScreenUiStateManager.DogsScreenEffect> {

    @Immutable
    sealed class DogsScreenEvent : UiStateManager.ViewEvent {
        object Loading : DogsScreenEvent()
        data class DogDetail(val dog: Dog) : DogsScreenEvent()
        data class UpdateDogIsFollowed(val dog: Dog) : DogsScreenEvent()
        object OpenSettings : DogsScreenEvent()
        object CloseSettings : DogsScreenEvent()
        data class DataReceived(val list: List<Dog>? = null, val errorMessage: String? = null) : DogsScreenEvent()
        data class FavoriteAnimalCountChanged(val favoriteList: List<Dog>) : DogsScreenEvent()
        data class DataReceivedWithSearch(val list: List<Dog>? = null, val errorMessage: String? = null) : DogsScreenEvent()
        data class DataChanged(val list: List<Dog>? = null) : DogsScreenEvent()
        data class SettingsUpdated(val viewTypeDogs: ViewTypeForList, val viewTypeForSettings: ViewTypeForSettings, val searchType: SearchType) : DogsScreenEvent()
        data class LoadSettings(val searchType: String?, val settingsType: String?, val listType: String?) : DogsScreenEvent()
        data class ShowBigImage(val dog: Dog) : DogsScreenEvent()
        object CloseBigImage : DogsScreenEvent()
    }

    @Immutable
    sealed class DogsScreenEffect : UiStateManager.ViewEffect {
        data class NavigateToDogDetail(val dog: Dog) : DogsScreenEffect()
    }

    @Immutable
    data class DogsScreenState(
        val loading: Boolean, val errorMessage: String?, val dogs: List<Dog>?,
        val dogsBackup: List<Dog>?, val viewTypeForList: ViewTypeForList = ViewTypeForList.LAZY_COLUMN,
        val viewTypeForSettings: ViewTypeForSettings = ViewTypeForSettings.POPUP,
        val searchType: SearchType = SearchType.LOCAL_SEARCH, val showSettings: Boolean, val favoriteAnimalCount: Int,
        val showBigImage: Boolean = false,
        val selectedDogForBigImage: Dog? = null,
    ) : UiStateManager.ViewState {
        companion object {
            fun initial(): DogsScreenState {
                return DogsScreenState(loading = true, errorMessage = null, dogs = null, dogsBackup = null, showSettings = false, favoriteAnimalCount = 0)
            }
        }
    }

    override fun handleEvent(
        previousState: DogsScreenState,
        event: DogsScreenEvent
    ): Pair<DogsScreenState, DogsScreenEffect?> {

        return when (event) {

            is DogsScreenEvent.Loading -> {
                previousState.copy(
                    loading = true
                ) to null
            }

            is DogsScreenEvent.DogDetail -> {
                previousState.copy() to DogsScreenEffect.NavigateToDogDetail(event.dog)
            }

            is DogsScreenEvent.DataReceived -> {
                previousState.copy(
                    loading = false,
                    errorMessage = event.errorMessage,
                    dogs = event.list,
                    dogsBackup = event.list
                ) to null
            }

            is DogsScreenEvent.DataReceivedWithSearch -> {
                previousState.copy(
                    loading = false,
                    errorMessage = event.errorMessage,
                    dogs = event.list,
                ) to null
            }

            is DogsScreenEvent.DataChanged -> {
                previousState.copy(
                    dogs = event.list
                ) to null
            }

            is DogsScreenEvent.SettingsUpdated -> {
                previousState.copy(
                    viewTypeForList = event.viewTypeDogs,
                    viewTypeForSettings = event.viewTypeForSettings,
                    searchType = event.searchType,
                    showSettings = false
                ) to null
            }

            is DogsScreenEvent.OpenSettings -> {
                previousState.copy(
                    showSettings = true
                ) to null
            }

            is DogsScreenEvent.CloseSettings -> {
                previousState.copy(
                    showSettings = false
                ) to null
            }

            is DogsScreenEvent.UpdateDogIsFollowed -> {
                previousState.dogs?.find { it.id == event.dog.id }?.isFavorite = event.dog.isFavorite
                previousState.dogsBackup?.find { it.id == event.dog.id }?.isFavorite = event.dog.isFavorite
                previousState.copy(dogsBackup = previousState.dogsBackup, dogs = previousState.dogs) to null
            }

            is DogsScreenEvent.FavoriteAnimalCountChanged -> {
                previousState.dogs?.map { it.isFavorite = false }
                previousState.dogsBackup?.map { it.isFavorite = false }

                event.favoriteList?.forEach { favoriteAnimal ->
                    previousState.dogs?.find { it.id == favoriteAnimal.id }?.isFavorite = true
                    previousState.dogsBackup?.find { it.id == favoriteAnimal.id }?.isFavorite = true
                }

                previousState.copy(dogs = previousState.dogs, dogsBackup = previousState.dogsBackup, favoriteAnimalCount = event.favoriteList.size) to null
            }

            is DogsScreenEvent.LoadSettings -> {
                previousState.copy(
                    searchType = enumValueOf(event.searchType ?: SearchType.LOCAL_SEARCH.name) as SearchType,
                    viewTypeForList = enumValueOf(event.listType ?: ViewTypeForList.LAZY_COLUMN.name) as ViewTypeForList,
                    viewTypeForSettings = enumValueOf(event.settingsType ?: ViewTypeForSettings.POPUP.name) as ViewTypeForSettings
                ) to null
            }

            is DogsScreenEvent.ShowBigImage -> {
                previousState.copy(showBigImage = true, selectedDogForBigImage = event.dog) to null
            }

            is DogsScreenEvent.CloseBigImage -> {
                previousState.copy(showBigImage = false) to null
            }

        }
    }

}