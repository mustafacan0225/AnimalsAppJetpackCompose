package com.mustafacan.ui_cats.feature.catdetail

import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Immutable
import com.mustafacan.domain.model.cats.Cat
import com.mustafacan.ui_common.model.enums.ViewTypeForTab
import com.mustafacan.ui_common.viewmodel.UiStateManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class CatDetailScreenUiStateManager() :
    UiStateManager<CatDetailScreenUiStateManager.CatDetailScreenState, CatDetailScreenUiStateManager.CatDetailScreenEvent, CatDetailScreenUiStateManager.CatDetailScreenEffect> {

    @Immutable
    sealed class CatDetailScreenEvent : UiStateManager.ViewEvent {
        data class Load(val cat: Cat, val pagerState: PagerState, val tabType: String?, val tabList: List<Pair<Int, Int>>, val scope: CoroutineScope) : CatDetailScreenEvent()
        data class OnClickTabItem(val index: Int): CatDetailScreenEvent()
        object OpenSettings : CatDetailScreenEvent()
        object CloseSettings : CatDetailScreenEvent()
        data class SettingsUpdated(val viewTypeForTab: ViewTypeForTab) : CatDetailScreenEvent()
        object UpdateCatIsFavorite : CatDetailScreenEvent()
        object ShowBigImage : CatDetailScreenEvent()
        object CloseBigImage : CatDetailScreenEvent()
    }

    @Immutable
    sealed class CatDetailScreenEffect : UiStateManager.ViewEffect {
        data class NavigateToCatDetail(val cat: Cat) : CatDetailScreenEffect()
        //object NavigateToSettings : CatsScreenEffect()
    }

    @Immutable
    data class CatDetailScreenState(
        val initialLoaded: Boolean = false,
        val cat: Cat? = null,
        val isSelectedFavIcon: Boolean = false,
        val pagerState: PagerState? = null,
        val tabList: List<Pair<Int, Int>>? = null,
        val coroutineScope: CoroutineScope? = null,
        val showSettings: Boolean = false,
        val currentViewTypeForTab: ViewTypeForTab? = null,
        val showBigImage: Boolean = false
    ) : UiStateManager.ViewState {
        companion object {
            fun initial(): CatDetailScreenState {
                return CatDetailScreenState()
            }
        }
    }

    override fun handleEvent(
        previousState: CatDetailScreenState,
        event: CatDetailScreenEvent
    ): Pair<CatDetailScreenState, CatDetailScreenEffect?> {

        return when (event) {

            is CatDetailScreenEvent.Load -> {
                previousState.copy(
                    cat = event.cat,
                    pagerState = event.pagerState,
                    tabList = event.tabList,
                    coroutineScope = event.scope,
                    isSelectedFavIcon = event.cat.isFavorite?: false,
                    initialLoaded = true,
                    currentViewTypeForTab = enumValueOf(event.tabType ?: ViewTypeForTab.TAB_TYPE_CUSTOM_INDICATOR_WITH_LEADING_ICON.name) as ViewTypeForTab
                ) to null
            }

            is CatDetailScreenEvent.OnClickTabItem -> {
                previousState.coroutineScope?.launch {
                    previousState.pagerState?.scrollToPage(event.index)
                }
                previousState.copy(pagerState = previousState.pagerState) to null
            }

            is CatDetailScreenEvent.OpenSettings -> {
                previousState.copy(
                    showSettings = true
                ) to null
            }

            is CatDetailScreenEvent.CloseSettings -> {
                previousState.copy(
                    showSettings = false
                ) to null
            }

            is CatDetailScreenEvent.SettingsUpdated -> {
                previousState.copy(currentViewTypeForTab = event.viewTypeForTab, showSettings = false) to null
            }

            is CatDetailScreenEvent.UpdateCatIsFavorite -> {
                previousState.cat?.isFavorite = !previousState.cat?.isFavorite!!
                previousState.copy(cat = previousState.cat, isSelectedFavIcon = previousState.cat.isFavorite?: false) to null
            }

            is CatDetailScreenEvent.ShowBigImage -> {
                previousState.copy(showBigImage = true) to null
            }

            is CatDetailScreenEvent.CloseBigImage -> {
                previousState.copy(showBigImage = false) to null
            }

        }
    }

}