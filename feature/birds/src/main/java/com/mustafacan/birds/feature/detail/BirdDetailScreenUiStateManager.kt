package com.mustafacan.birds.feature.detail

import androidx.compose.runtime.Immutable
import com.mustafacan.domain.model.birds.Bird
import com.mustafacan.core.viewmodel.UiStateManager

class BirdDetailScreenUiStateManager() :
    UiStateManager<BirdDetailScreenUiStateManager.BirdDetailScreenState, BirdDetailScreenUiStateManager.BirdDetailScreenEvent, BirdDetailScreenUiStateManager.BirdDetailScreenEffect> {

    @Immutable
    sealed class BirdDetailScreenEvent : UiStateManager.ViewEvent {
        data class Load(val bird: Bird) : BirdDetailScreenEvent()
        object UpdateBirdIsFavorite : BirdDetailScreenEvent()
        object ShowBigImage : BirdDetailScreenEvent()
        object CloseBigImage : BirdDetailScreenEvent()
    }

    @Immutable
    sealed class BirdDetailScreenEffect : UiStateManager.ViewEffect {

    }

    @Immutable
    data class BirdDetailScreenState(
        val initialLoaded: Boolean = false,
        val bird: Bird? = null,
        val isSelectedFavIcon: Boolean = false,
        val showSettings: Boolean = false,
        val showBigImage: Boolean = false
    ) : UiStateManager.ViewState {
        companion object {
            fun initial(): BirdDetailScreenState {
                return BirdDetailScreenState()
            }
        }
    }

    override fun handleEvent(
        previousState: BirdDetailScreenState,
        event: BirdDetailScreenEvent
    ): Pair<BirdDetailScreenState, BirdDetailScreenEffect?> {

        return when (event) {

            is BirdDetailScreenEvent.Load -> {
                previousState.copy(
                    bird = event.bird,
                    isSelectedFavIcon = event.bird.isFavorite?: false,
                    initialLoaded = true,
                ) to null
            }

            is BirdDetailScreenEvent.UpdateBirdIsFavorite -> {
                previousState.bird?.isFavorite = !previousState.bird?.isFavorite!!
                previousState.copy(bird = previousState.bird, isSelectedFavIcon = previousState.bird.isFavorite?: false) to null
            }

            is BirdDetailScreenEvent.ShowBigImage -> {
                previousState.copy(showBigImage = true) to null
            }

            is BirdDetailScreenEvent.CloseBigImage -> {
                previousState.copy(showBigImage = false) to null
            }

        }
    }

}