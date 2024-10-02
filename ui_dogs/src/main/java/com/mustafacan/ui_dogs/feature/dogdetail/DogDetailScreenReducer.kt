package com.mustafacan.ui_dogs.feature.dogdetail

import android.util.Log
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Immutable
import com.mustafacan.domain.model.dogs.Dog
import com.mustafacan.domain.usecase.dogs.sharedpref_usecase.GetTabTypeUseCase
import com.mustafacan.ui_common.model.enums.ViewTypeForTab
import com.mustafacan.ui_common.viewmodel.Reducer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class DogDetailScreenReducer() :
    Reducer<DogDetailScreenReducer.DogDetailScreenState, DogDetailScreenReducer.DogDetailScreenEvent, DogDetailScreenReducer.DogDetailScreenEffect> {

    @Immutable
    sealed class DogDetailScreenEvent : Reducer.ViewEvent {
        data class Load(val dog: Dog, val pagerState: PagerState, val tabType: String?, val tabList: List<Pair<Int, Int>>, val scope: CoroutineScope) : DogDetailScreenEvent()
        data class OnClickTabItem(val index: Int): DogDetailScreenEvent()
        object OpenSettings : DogDetailScreenEvent()
        object CloseSettings : DogDetailScreenEvent()
        data class SettingsUpdated(val viewTypeForTab: ViewTypeForTab) : DogDetailScreenEvent()
        object UpdateDogIsFavorite : DogDetailScreenEvent()
        object ShowLikeAnimation : DogDetailScreenEvent()
        object HideLikeAnimation : DogDetailScreenEvent()
    }

    @Immutable
    sealed class DogDetailScreenEffect : Reducer.ViewEffect {
        data class NavigateToDogDetail(val dog: Dog) : DogDetailScreenEffect()
        //object NavigateToSettings : DogsScreenEffect()
    }

    @Immutable
    data class DogDetailScreenState(
        val initialLoaded: Boolean = false,
        val dog: Dog? = null,
        val isSelectedFavIcon: Boolean = false,
        val pagerState: PagerState? = null,
        val tabList: List<Pair<Int, Int>>? = null,
        val coroutineScope: CoroutineScope? = null,
        val showSettings: Boolean = false,
        val currentViewTypeForTab: ViewTypeForTab? = null,
        val likeAnimationVisibility: Boolean = false
    ) : Reducer.ViewState {
        companion object {
            fun initial(): DogDetailScreenState {
                return DogDetailScreenState()
            }
        }
    }

    override fun reduce(
        previousState: DogDetailScreenState,
        event: DogDetailScreenEvent
    ): Pair<DogDetailScreenState, DogDetailScreenEffect?> {

        return when (event) {

            is DogDetailScreenEvent.Load -> {
                previousState.copy(
                    dog = event.dog,
                    pagerState = event.pagerState,
                    tabList = event.tabList,
                    coroutineScope = event.scope,
                    isSelectedFavIcon = event.dog.isFavorite?: false,
                    initialLoaded = true,
                    currentViewTypeForTab = enumValueOf(event.tabType ?: ViewTypeForTab.TAB_TYPE_CUSTOM_INDICATOR_WITH_LEADING_ICON.name) as ViewTypeForTab
                ) to null
            }

            is DogDetailScreenEvent.OnClickTabItem -> {
                previousState.coroutineScope?.launch {
                    previousState.pagerState?.scrollToPage(event.index)
                }
                previousState.copy(pagerState = previousState.pagerState) to null
            }

            is DogDetailScreenEvent.OpenSettings -> {
                previousState.copy(
                    showSettings = true
                ) to null
            }

            is DogDetailScreenEvent.CloseSettings -> {
                previousState.copy(
                    showSettings = false
                ) to null
            }

            is DogDetailScreenEvent.SettingsUpdated -> {
                previousState.copy(currentViewTypeForTab = event.viewTypeForTab, showSettings = false) to null
            }

            is DogDetailScreenEvent.UpdateDogIsFavorite -> {
                previousState.dog?.isFavorite = !previousState.dog?.isFavorite!!
                previousState.copy(dog = previousState.dog, isSelectedFavIcon = previousState.dog.isFavorite?: false) to null
            }

            is DogDetailScreenEvent.ShowLikeAnimation -> {
                previousState.copy(likeAnimationVisibility = true) to null
            }

            is DogDetailScreenEvent.HideLikeAnimation -> {
                previousState.copy(likeAnimationVisibility = false) to null
            }


        }
    }

}