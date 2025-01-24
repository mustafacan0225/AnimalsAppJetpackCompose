package com.mustafacan.birds.feature.detail

import android.content.Context
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.mustafacan.domain.model.birds.Bird
import com.mustafacan.domain.usecase.birds.roomdb_usecase.AddFavoriteBirdUseCase
import com.mustafacan.domain.usecase.birds.roomdb_usecase.DeleteFavoriteBirdUseCase
import com.mustafacan.core.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BirdDetailViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val savedStateHandle: SavedStateHandle,
    private val addFavoriteBirdUseCase: AddFavoriteBirdUseCase,
    private val deleteFavoriteBirdUseCase: DeleteFavoriteBirdUseCase
) : BaseViewModel<BirdDetailScreenUiStateManager.BirdDetailScreenState, BirdDetailScreenUiStateManager.BirdDetailScreenEvent,
        BirdDetailScreenUiStateManager.BirdDetailScreenEffect>(
    initialState = BirdDetailScreenUiStateManager.BirdDetailScreenState.initial(),
    uiStateManager = BirdDetailScreenUiStateManager()
) {

    val bird: Bird? = savedStateHandle["bird"]

    fun load() {
        viewModelScope.launch {
            sendEvent(BirdDetailScreenUiStateManager.BirdDetailScreenEvent.Load(bird = bird!!))
        }
    }

    fun updateIsFavorite() {
        viewModelScope.launch {
            if (state.value.bird?.isFavorite?: false) {
                if (deleteFavoriteBirdUseCase.runUseCase(bird!!)) {
                    Log.d("room-test", "deleted favorite bird " + bird.name)
                    sendEvent(BirdDetailScreenUiStateManager.BirdDetailScreenEvent.UpdateBirdIsFavorite)
                }
            } else {
                if (addFavoriteBirdUseCase.runUseCase(bird!!.copy(isFavorite = true))) {
                    Log.d("room-test", "added favorite bird " + bird.name)
                    sendEvent(BirdDetailScreenUiStateManager.BirdDetailScreenEvent.UpdateBirdIsFavorite)
                }

            }
        }
    }

    fun showBigImage() {
        sendEvent(BirdDetailScreenUiStateManager.BirdDetailScreenEvent.ShowBigImage)
    }

    fun closeBigImage() {
        sendEvent(BirdDetailScreenUiStateManager.BirdDetailScreenEvent.CloseBigImage)
    }


}