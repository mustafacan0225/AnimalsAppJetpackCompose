package com.mustafacan.animalsapp.ui.screen.dogs

import androidx.compose.runtime.Immutable
import com.mustafacan.animalsapp.ui.base.Reducer
import com.mustafacan.animalsapp.ui.model.enums.SearchType
import com.mustafacan.animalsapp.ui.model.enums.ViewTypeForList
import com.mustafacan.animalsapp.ui.model.enums.ViewTypeForSettings
import com.mustafacan.data.local.LocalDataSource
import com.mustafacan.data.local.datasource.sharedpref.dogs.LocalDataSourceDogs
import com.mustafacan.domain.model.dogs.Dog
import kotlinx.coroutines.flow.Flow

class DogsScreenReducer () :Reducer<DogsScreenReducer.DogsScreenState, DogsScreenReducer.DogsScreenEvent, DogsScreenReducer.DogsScreenEffect> {

    @Immutable
    sealed class DogsScreenEvent : Reducer.ViewEvent {
        object Loading : DogsScreenEvent()
        data class DogDetailWithId(val dogId: String) : DogsScreenEvent()
        data class DogDetail(val dog: Dog) : DogsScreenEvent()
        data class UpdateDogIsFollowed(val dogId: String, val isFollowed: Boolean) : DogsScreenEvent()
        object OpenSettings : DogsScreenEvent()
        object CloseSettings : DogsScreenEvent()
        data class DataReceived(val list: List<Dog>? = null, val errorMessage: String? = null) : DogsScreenEvent()
        data class DataReceivedWithSearch(val list: List<Dog>? = null, val errorMessage: String? = null) : DogsScreenEvent()
        data class DataChanged(val list: List<Dog>? = null) : DogsScreenEvent()
        data class SettingsUpdated(val viewTypeDogs: ViewTypeForList, val viewTypeForSettings: ViewTypeForSettings, val searchType: SearchType) : DogsScreenEvent()
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
        val testValue: Flow<Int>
    ) : Reducer.ViewState {
        companion object {
             fun initial(localDataSource: LocalDataSource, localDataSourceDogs: LocalDataSourceDogs): DogsScreenState {
                return DogsScreenState(
                    loading = true,
                    errorMessage = null,
                    dogs = null,
                    dogsBackup = null,
                    searchType = enumValueOf(localDataSourceDogs.getSearchTypeForDogList() ?: SearchType.LOCAL_SEARCH.name) as SearchType,
                    viewTypeForList = enumValueOf(localDataSourceDogs.getListTypeForDogList() ?: ViewTypeForList.LAZY_COLUMN.name) as ViewTypeForList,
                    viewTypeForSettings = enumValueOf(localDataSourceDogs.getSettingsTypeForDogList() ?: ViewTypeForSettings.POPUP.name) as ViewTypeForSettings,
                    showSettings = false,
                    testValue = localDataSource.getTestFlow()
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

            is DogsScreenEvent.DogDetailWithId -> TODO()
            is DogsScreenEvent.UpdateDogIsFollowed -> TODO()
        }
    }

}