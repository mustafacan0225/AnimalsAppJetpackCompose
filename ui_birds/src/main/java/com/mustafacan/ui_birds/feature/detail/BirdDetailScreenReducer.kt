package com.mustafacan.ui_birds.feature.detail

import androidx.compose.runtime.Immutable
import com.mustafacan.domain.model.birds.Bird
import com.mustafacan.ui_common.viewmodel.Reducer

class BirdDetailScreenReducer() :
    Reducer<BirdDetailScreenReducer.BirdDetailScreenState, BirdDetailScreenReducer.BirdDetailScreenEvent, BirdDetailScreenReducer.BirdDetailScreenEffect> {

    @Immutable
    sealed class BirdDetailScreenEvent : Reducer.ViewEvent {
        data class Load(val bird: Bird) : BirdDetailScreenEvent()
        object UpdateBirdIsFavorite : BirdDetailScreenEvent()
        object ShowBigImage : BirdDetailScreenEvent()
        object CloseBigImage : BirdDetailScreenEvent()
    }

    @Immutable
    sealed class BirdDetailScreenEffect : Reducer.ViewEffect {

    }

    @Immutable
    data class BirdDetailScreenState(
        val initialLoaded: Boolean = false,
        val bird: Bird? = null,
        val isSelectedFavIcon: Boolean = false,
        val showSettings: Boolean = false,
        val showBigImage: Boolean = false
    ) : Reducer.ViewState {
        companion object {
            fun initial(): BirdDetailScreenState {
                return BirdDetailScreenState()
            }
        }
    }

    override fun reduce(
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