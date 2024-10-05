package com.mustafacan.ui_cats.feature.catdetail

import android.content.Context
import android.util.Log
import androidx.compose.foundation.pager.PagerState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.mustafacan.domain.model.cats.Cat
import com.mustafacan.domain.usecase.cats.roomdb_usecase.AddFavoriteCatUseCase
import com.mustafacan.domain.usecase.cats.roomdb_usecase.DeleteFavoriteCatUseCase
import com.mustafacan.domain.usecase.cats.sharedpref_usecase.GetTabTypeUseCase
import com.mustafacan.domain.usecase.cats.sharedpref_usecase.SaveTabTypeUseCase
import com.mustafacan.ui_common.model.enums.ViewTypeForTab
import com.mustafacan.ui_common.viewmodel.BaseViewModel
import com.mustafacan.ui_cats.R
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
) : BaseViewModel<CatDetailScreenReducer.CatDetailScreenState, CatDetailScreenReducer.CatDetailScreenEvent,
        CatDetailScreenReducer.CatDetailScreenEffect>(
    initialState = CatDetailScreenReducer.CatDetailScreenState.initial(),
    reducer = CatDetailScreenReducer()
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
            sendEvent(CatDetailScreenReducer.CatDetailScreenEvent.Load(cat = cat!!, pagerState = pagerState, tabType = tabType, tabList = tabList, scope = scope))

        }
    }

    fun onClickTab(index: Int) {
        sendEvent(CatDetailScreenReducer.CatDetailScreenEvent.OnClickTabItem(index))
    }

    fun navigateToSettings() {
        sendEvent(CatDetailScreenReducer.CatDetailScreenEvent.OpenSettings)
    }

    fun closeSettings() {
        sendEvent(CatDetailScreenReducer.CatDetailScreenEvent.CloseSettings)
    }

    fun settingsUpdated(viewTypeForTab: ViewTypeForTab) {
        viewModelScope.launch {
            saveTabTypeUseCase.runUseCase(viewTypeForTab.name)
            sendEvent(CatDetailScreenReducer.CatDetailScreenEvent.SettingsUpdated(viewTypeForTab))
        }
    }

    fun updateIsFavorite() {
        viewModelScope.launch {
            if (state.value.cat?.isFavorite?: false) {
                if (deleteFavoriteCatUseCase.runUseCase(cat!!)) {
                    Log.d("room-test", "deleted favorite cat " + cat.name)
                    sendEvent(CatDetailScreenReducer.CatDetailScreenEvent.UpdateCatIsFavorite)
                }
            } else {
                if (addFavoriteCatUseCase.runUseCase(cat!!.copy(isFavorite = true))) {
                    Log.d("room-test", "added favorite cat " + cat.name)
                    sendEvent(CatDetailScreenReducer.CatDetailScreenEvent.UpdateCatIsFavorite)
                }

            }
        }
    }

    fun showBigImage() {
        sendEvent(CatDetailScreenReducer.CatDetailScreenEvent.ShowBigImage)
    }

    fun closeBigImage() {
        sendEvent(CatDetailScreenReducer.CatDetailScreenEvent.CloseBigImage)
    }

}