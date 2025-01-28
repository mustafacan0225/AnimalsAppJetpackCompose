package com.mustafacan.cats.feature.catdetail

import android.content.Context
import android.util.Log
import androidx.compose.foundation.pager.PagerState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.mustafacan.domain.model.cats.Cat
import com.mustafacan.domain.usecase.cats.roomdb.AddFavoriteCatUseCase
import com.mustafacan.domain.usecase.cats.roomdb.DeleteFavoriteCatUseCase
import com.mustafacan.domain.usecase.cats.sharedpreference.GetTabTypeUseCase
import com.mustafacan.domain.usecase.cats.sharedpreference.SaveTabTypeUseCase
import com.mustafacan.core.model.enums.ViewTypeForTab
import com.mustafacan.core.viewmodel.BaseViewModel
import com.mustafacan.cats.R
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatDetailViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val savedStateHandle: SavedStateHandle,
    private val addFavoriteCatUseCase: AddFavoriteCatUseCase,
    private val deleteFavoriteCatUseCase: DeleteFavoriteCatUseCase,
    private val getTabTypeUseCase: GetTabTypeUseCase,
    private val saveTabTypeUseCase: SaveTabTypeUseCase
) : BaseViewModel<CatDetailScreenUiStateManager.CatDetailScreenState, CatDetailScreenUiStateManager.CatDetailScreenEvent,
        CatDetailScreenUiStateManager.CatDetailScreenEffect>(
    initialState = CatDetailScreenUiStateManager.CatDetailScreenState.initial(),
    uiStateManager = CatDetailScreenUiStateManager()
) {

    val cat: Cat? = savedStateHandle["cat"]

    val tabList = listOf(
        R.string.tab_general to R.drawable.paw,
        R.string.tab_info to R.drawable.kitten,
        R.string.tab_temperament to R.drawable.catsmile,
        R.string.tab_colors to R.drawable.colors
    )

    fun load(pagerState: PagerState, scope: CoroutineScope) {
        viewModelScope.launch {
            val tabType = getTabTypeUseCase.runUseCase()
            sendEvent(
                CatDetailScreenUiStateManager.CatDetailScreenEvent.Load(
                    cat = cat!!,
                    pagerState = pagerState,
                    tabType = tabType,
                    tabList = tabList,
                    scope = scope
                )
            )

        }
    }

    fun onClickTab(index: Int) {
        sendEvent(CatDetailScreenUiStateManager.CatDetailScreenEvent.OnClickTabItem(index))
    }

    fun navigateToSettings() {
        sendEvent(CatDetailScreenUiStateManager.CatDetailScreenEvent.OpenSettings)
    }

    fun closeSettings() {
        sendEvent(CatDetailScreenUiStateManager.CatDetailScreenEvent.CloseSettings)
    }

    fun settingsUpdated(viewTypeForTab: ViewTypeForTab) {
        viewModelScope.launch {
            saveTabTypeUseCase.runUseCase(viewTypeForTab.name)
            sendEvent(
                CatDetailScreenUiStateManager.CatDetailScreenEvent.SettingsUpdated(
                    viewTypeForTab
                )
            )
        }
    }

    fun updateIsFavorite() {
        viewModelScope.launch {
            if (state.value.cat?.isFavorite?: false) {
                if (deleteFavoriteCatUseCase.runUseCase(cat!!)) {
                    Log.d("room-test", "deleted favorite cat " + cat.name)
                    sendEvent(CatDetailScreenUiStateManager.CatDetailScreenEvent.UpdateCatIsFavorite)
                }
            } else {
                if (addFavoriteCatUseCase.runUseCase(cat!!.copy(isFavorite = true))) {
                    Log.d("room-test", "added favorite cat " + cat.name)
                    sendEvent(CatDetailScreenUiStateManager.CatDetailScreenEvent.UpdateCatIsFavorite)
                }

            }
        }
    }

    fun showBigImage() {
        sendEvent(CatDetailScreenUiStateManager.CatDetailScreenEvent.ShowBigImage)
    }

    fun closeBigImage() {
        sendEvent(CatDetailScreenUiStateManager.CatDetailScreenEvent.CloseBigImage)
    }

}