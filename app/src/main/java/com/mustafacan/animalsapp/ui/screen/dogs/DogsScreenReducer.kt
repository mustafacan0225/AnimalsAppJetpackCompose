package com.mustafacan.animalsapp.ui.screen.dogs

import androidx.compose.runtime.Immutable
import com.mustafacan.animalsapp.ui.base.Reducer
import com.mustafacan.domain.model.dogs.Dog

class DogsScreenReducer :Reducer<DogsScreenReducer.DogsScreenState, DogsScreenReducer.DogsScreenEvent, DogsScreenReducer.DogsScreenEffect> {

    @Immutable
    sealed class DogsScreenEvent : Reducer.ViewEvent {
        object Loading : DogsScreenEvent()
        data class OnClickDog(val dogId: String) : DogsScreenEvent()
        data class UpdateDogIsFollowed(val dogId: String, val isFollowed: Boolean) :
            DogsScreenEvent()
        object Retry : DogsScreenEvent()
        data class DataReceived(val list: List<Dog>? = null, val errorMessage: String? = null) : DogsScreenEvent()
    }

    @Immutable
    sealed class DogsScreenEffect : Reducer.ViewEffect {
        data class NavigateToDogDetail(val dogId: String) : DogsScreenEffect()
        object NavigateToSettings : DogsScreenEffect()
    }

    @Immutable
    data class DogsScreenState(
        val loading: Boolean,
        val errorMessage: String?,
        val dogs: List<Dog>?,
    ) : Reducer.ViewState {
        companion object {
            fun initial(): DogsScreenState {

                return DogsScreenState(
                    loading = true,
                    errorMessage = null,
                    dogs = null,
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

            is DogsScreenEvent.OnClickDog -> {
                previousState to DogsScreenEffect.NavigateToDogDetail(event.dogId)
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

            else -> {
                previousState to null
            }

        }
    }

}