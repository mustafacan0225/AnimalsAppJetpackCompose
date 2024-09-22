package com.mustafacan.ui_dogs.feature.dogs

import androidx.compose.runtime.Immutable
import com.mustafacan.data.local.LocalDataSource
import com.mustafacan.data.local.datasource.sharedpref.dogs.LocalDataSourceDogs
import com.mustafacan.domain.model.dogs.Dog
import com.mustafacan.ui_common.model.enums.SearchType
import com.mustafacan.ui_common.model.enums.ViewTypeForList
import com.mustafacan.ui_common.model.enums.ViewTypeForSettings
import com.mustafacan.ui_common.viewmodel.Reducer
import kotlinx.coroutines.flow.Flow

class DogsScreenReducer() :
    Reducer<DogsScreenReducer.DogsScreenState, DogsScreenReducer.DogsScreenEvent, DogsScreenReducer.DogsScreenEffect> {

    @Immutable
    sealed class DogsScreenEvent : Reducer.ViewEvent {
        object Loading : DogsScreenEvent()
        data class DogDetailWithId(val dogId: String) : DogsScreenEvent()
        data class DogDetail(val dog: Dog) : DogsScreenEvent()
        data class UpdateDogIsFollowed(val dog: Dog) : DogsScreenEvent()
        object OpenSettings : DogsScreenEvent()
        object CloseSettings : DogsScreenEvent()
        data class DataReceived(val list: List<Dog>? = null, val errorMessage: String? = null) :
            DogsScreenEvent()
        data class FavoriteAnimalCountChanged(val favoriteList: List<Dog>): DogsScreenEvent()

        data class DataReceivedWithSearch(
            val list: List<Dog>? = null,
            val errorMessage: String? = null
        ) : DogsScreenEvent()

        data class DataChanged(val list: List<Dog>? = null) : DogsScreenEvent()

        data class SettingsUpdated(
            val viewTypeDogs: ViewTypeForList,
            val viewTypeForSettings: ViewTypeForSettings,
            val searchType: SearchType
        ) : DogsScreenEvent()
    }

    @Immutable
    sealed class DogsScreenEffect : Reducer.ViewEffect {
        data class NavigateToDogDetail(val dog: Dog) : DogsScreenEffect()
        //object NavigateToSettings : DogsScreenEffect()
    }

    @Immutable
    data class DogsScreenState(
        val loading: Boolean,
        val errorMessage: String?,
        val dogs: List<Dog>?,
        val dogsBackup: List<Dog>?,
        val viewTypeForList: ViewTypeForList,
        val viewTypeForSettings: ViewTypeForSettings,
        val searchType: SearchType,
        val showSettings: Boolean,
        val testValue: Flow<Int>,
        val favoriteAnimalCount: Int
    ) : Reducer.ViewState {
        companion object {
            fun initial(
                localDataSource: LocalDataSource,
                localDataSourceDogs: LocalDataSourceDogs
            ): DogsScreenState {
                return DogsScreenState(
                    loading = true,
                    errorMessage = null,
                    dogs = null,
                    dogsBackup = null,
                    searchType = enumValueOf(
                        localDataSourceDogs.getSearchTypeForDogList()
                            ?: SearchType.LOCAL_SEARCH.name
                    ) as SearchType,
                    viewTypeForList = enumValueOf(
                        localDataSourceDogs.getListTypeForDogList()
                            ?: ViewTypeForList.LAZY_COLUMN.name
                    ) as ViewTypeForList,
                    viewTypeForSettings = enumValueOf(
                        localDataSourceDogs.getSettingsTypeForDogList()
                            ?: ViewTypeForSettings.POPUP.name
                    ) as ViewTypeForSettings,
                    showSettings = false,
                    testValue = localDataSource.getTestFlow(),
                    favoriteAnimalCount = 0
                )
            }
        }
    }

    override fun reduce(
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

            is DogsScreenEvent.DogDetailWithId -> TODO()

        }
    }

}