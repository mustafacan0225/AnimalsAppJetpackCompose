package com.mustafacan.animalsapp.ui.screen.dogs

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import com.mustafacan.animalsapp.ui.base.Reducer
import com.mustafacan.animalsapp.ui.model.enums.ViewTypeForList
import com.mustafacan.animalsapp.ui.model.enums.ViewTypeForSettings
import com.mustafacan.data.local.LocalDataSource
import com.mustafacan.domain.model.dogs.Dog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class DogsScreenReducer () :Reducer<DogsScreenReducer.DogsScreenState, DogsScreenReducer.DogsScreenEvent, DogsScreenReducer.DogsScreenEffect> {

    @Immutable
    sealed class DogsScreenEvent : Reducer.ViewEvent {
        object Loading : DogsScreenEvent()
        data class DogDetailWithId(val dogId: String) : DogsScreenEvent()
        data class DogDetail(val dog: Dog) : DogsScreenEvent()
        data class UpdateDogIsFollowed(val dogId: String, val isFollowed: Boolean) : DogsScreenEvent()
        object Retry : DogsScreenEvent()
        object OpenSettings : DogsScreenEvent()
        object CloseSettings : DogsScreenEvent()
        data class DataReceived(val list: List<Dog>? = null, val errorMessage: String? = null) : DogsScreenEvent()
        data class SettingsUpdated(val viewTypeDogs: ViewTypeForList, val viewTypeForSettings: ViewTypeForSettings) : DogsScreenEvent()
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
        val viewTypeForList: ViewTypeForList,
        val viewTypeForSettings: ViewTypeForSettings,
        val showSettings: Boolean,
        val testValue: Flow<Int>
    ) : Reducer.ViewState {
        companion object {
             fun initial(localDataSource: LocalDataSource): DogsScreenState {
                return DogsScreenState(
                    loading = true,
                    errorMessage = null,
                    dogs = null,
                    viewTypeForList = ViewTypeForList.LAZY_COLUMN,
                    viewTypeForSettings = ViewTypeForSettings.POPUP,
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
                    loading = true,
                    errorMessage = null,
                    dogs = null
                ) to null
            }

            is DogsScreenEvent.DogDetail -> {
                println("called event ${event.dog.name}")
                previousState.copy() to DogsScreenEffect.NavigateToDogDetail(event.dog)
            }

            is DogsScreenEvent.Retry -> {
                previousState.copy(
                    loading = true
                ) to null
            }

            is DogsScreenEvent.DataReceived -> {
                previousState.copy(
                    loading = false,
                    errorMessage = event.errorMessage,
                    dogs = event.list
                ) to null
            }

            is DogsScreenEvent.SettingsUpdated -> {
                previousState.copy(
                    viewTypeForList = event.viewTypeDogs,
                    viewTypeForSettings = event.viewTypeForSettings,
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