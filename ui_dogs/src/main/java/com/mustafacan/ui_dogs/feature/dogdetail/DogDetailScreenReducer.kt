package com.mustafacan.ui_dogs.feature.dogdetail

import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Immutable
import com.mustafacan.data.local.datasource.sharedpref.dogs.LocalDataSourceDogs
import com.mustafacan.domain.model.dogs.Dog
import com.mustafacan.ui_common.model.enums.ViewTypeForTab
import com.mustafacan.ui_common.viewmodel.Reducer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class DogDetailScreenReducer() :
    Reducer<DogDetailScreenReducer.DogDetailScreenState, DogDetailScreenReducer.DogDetailScreenEvent, DogDetailScreenReducer.DogDetailScreenEffect> {

    @Immutable
    sealed class DogDetailScreenEvent : Reducer.ViewEvent {
        data class Load(val dog: Dog, val pagerState: PagerState, val tabList: List<Pair<Int, Int>>, val scope: CoroutineScope) : DogDetailScreenEvent()
        data class OnClickTabItem(val index: Int): DogDetailScreenEvent()
        object OpenSettings : DogDetailScreenEvent()
        object CloseSettings : DogDetailScreenEvent()
        data class SettingsUpdated(
            val viewTypeForTab: ViewTypeForTab
        ) : DogDetailScreenEvent()
    }

    @Immutable
    sealed class DogDetailScreenEffect : Reducer.ViewEffect {
        data class NavigateToDogDetail(val dog: Dog) : DogDetailScreenEffect()
        //object NavigateToSettings : DogsScreenEffect()
    }

    @Immutable
    data class DogDetailScreenState(
        val dog: Dog? = null,
        val pagerState: PagerState? = null,
        val tabList: List<Pair<Int, Int>>? = null,
        val coroutineScope: CoroutineScope? = null,
        val showSettings: Boolean,
        val currentViewTypeForTab: ViewTypeForTab
    ) : Reducer.ViewState {
        companion object {
            fun initial(localDataSourceDogs: LocalDataSourceDogs): DogDetailScreenState {
                return DogDetailScreenState(showSettings = false,
                    currentViewTypeForTab = enumValueOf(localDataSourceDogs.getTabTypeForDogDetail() ?: ViewTypeForTab.TAB_TYPE_CUSTOM_INDICATOR_WITH_LEADING_ICON.name) as ViewTypeForTab )
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
                    coroutineScope = event.scope
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


        }
    }

}