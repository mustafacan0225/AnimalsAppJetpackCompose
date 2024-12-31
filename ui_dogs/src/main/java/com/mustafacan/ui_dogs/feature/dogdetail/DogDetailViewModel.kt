package com.mustafacan.ui_dogs.feature.dogdetail

import android.content.Context
import android.util.Log
import androidx.compose.foundation.pager.PagerState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.mustafacan.domain.model.dogs.Dog
import com.mustafacan.domain.usecase.dogs.roomdb_usecase.AddFavoriteDogUseCase
import com.mustafacan.domain.usecase.dogs.roomdb_usecase.DeleteFavoriteDogUseCase
import com.mustafacan.domain.usecase.dogs.sharedpref_usecase.GetTabTypeUseCase
import com.mustafacan.domain.usecase.dogs.sharedpref_usecase.SaveTabTypeUseCase
import com.mustafacan.ui_common.model.enums.ViewTypeForTab
import com.mustafacan.ui_common.viewmodel.BaseViewModel
import com.mustafacan.ui_dogs.R
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DogDetailViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val savedStateHandle: SavedStateHandle,
    private val addFavoriteDogUseCase: AddFavoriteDogUseCase,
    private val deleteFavoriteDogUseCase: DeleteFavoriteDogUseCase,
    private val getTabTypeUseCase: GetTabTypeUseCase,
    private val saveTabTypeUseCase: SaveTabTypeUseCase
) : BaseViewModel<DogDetailScreenUiStateManager.DogDetailScreenState, DogDetailScreenUiStateManager.DogDetailScreenEvent,
        DogDetailScreenUiStateManager.DogDetailScreenEffect>(
    initialState = DogDetailScreenUiStateManager.DogDetailScreenState.initial(),
    uiStateManager = DogDetailScreenUiStateManager()
) {

    val dog: Dog? = savedStateHandle["dog"]

    val tabList = listOf(
        R.string.tab_general to R.drawable.general,
        R.string.tab_info to R.drawable.dog,
        R.string.tab_temperament to R.drawable.temperament,
        R.string.tab_colors to R.drawable.colors
    )

    fun load(pagerState: PagerState, scope: CoroutineScope) {
        viewModelScope.launch {
            val tabType = getTabTypeUseCase.runUseCase()
            sendEvent(DogDetailScreenUiStateManager.DogDetailScreenEvent.Load(dog = dog!!, pagerState = pagerState, tabType = tabType, tabList = tabList, scope = scope))

        }
    }

    fun onClickTab(index: Int) {
        sendEvent(DogDetailScreenUiStateManager.DogDetailScreenEvent.OnClickTabItem(index))
    }

    fun navigateToSettings() {
        sendEvent(DogDetailScreenUiStateManager.DogDetailScreenEvent.OpenSettings)
    }

    fun closeSettings() {
        sendEvent(DogDetailScreenUiStateManager.DogDetailScreenEvent.CloseSettings)
    }

    fun settingsUpdated(viewTypeForTab: ViewTypeForTab) {
        viewModelScope.launch {
            saveTabTypeUseCase.runUseCase(viewTypeForTab.name)
            sendEvent(DogDetailScreenUiStateManager.DogDetailScreenEvent.SettingsUpdated(viewTypeForTab))
        }
    }

    fun updateIsFavorite() {
        viewModelScope.launch {
            if (state.value.dog?.isFavorite?: false) {
                if (deleteFavoriteDogUseCase.runUseCase(dog!!)) {
                    Log.d("room-test", "deleted favorite dog " + dog.name)
                    sendEvent(DogDetailScreenUiStateManager.DogDetailScreenEvent.UpdateDogIsFavorite)
                }
            } else {
                if (addFavoriteDogUseCase.runUseCase(dog!!.copy(isFavorite = true))) {
                    Log.d("room-test", "added favorite dog " + dog.name)
                    sendEvent(DogDetailScreenUiStateManager.DogDetailScreenEvent.UpdateDogIsFavorite)
                }

            }
        }
    }

    fun showBigImage() {
        sendEvent(DogDetailScreenUiStateManager.DogDetailScreenEvent.ShowBigImage)
    }

    fun closeBigImage() {
        sendEvent(DogDetailScreenUiStateManager.DogDetailScreenEvent.CloseBigImage)
    }

}