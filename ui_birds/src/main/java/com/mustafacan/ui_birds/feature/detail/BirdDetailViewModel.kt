package com.mustafacan.ui_birds.feature.detail

import android.content.Context
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.mustafacan.domain.model.birds.Bird
import com.mustafacan.domain.usecase.birds.roomdb_usecase.AddFavoriteBirdUseCase
import com.mustafacan.domain.usecase.birds.roomdb_usecase.DeleteFavoriteBirdUseCase
import com.mustafacan.ui_common.viewmodel.BaseViewModel
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
) : BaseViewModel<BirdDetailScreenReducer.BirdDetailScreenState, BirdDetailScreenReducer.BirdDetailScreenEvent,
        BirdDetailScreenReducer.BirdDetailScreenEffect>(
    initialState = BirdDetailScreenReducer.BirdDetailScreenState.initial(),
    reducer = BirdDetailScreenReducer()
) {

    val bird: Bird? = savedStateHandle["bird"]

    fun load() {
        viewModelScope.launch {
            sendEvent(BirdDetailScreenReducer.BirdDetailScreenEvent.Load(bird = bird!!))

        }
    }

    fun updateIsFavorite() {
        viewModelScope.launch {
            if (state.value.bird?.isFavorite?: false) {
                if (deleteFavoriteBirdUseCase.runUseCase(bird!!)) {
                    Log.d("room-test", "deleted favorite bird " + bird.name)
                    sendEvent(BirdDetailScreenReducer.BirdDetailScreenEvent.UpdateBirdIsFavorite)
                }
            } else {
                if (addFavoriteBirdUseCase.runUseCase(bird!!.copy(isFavorite = true))) {
                    Log.d("room-test", "added favorite bird " + bird.name)
                    sendEvent(BirdDetailScreenReducer.BirdDetailScreenEvent.UpdateBirdIsFavorite)
                }

            }
        }
    }

    fun showBigImage() {
        sendEvent(BirdDetailScreenReducer.BirdDetailScreenEvent.ShowBigImage)
    }

    fun closeBigImage() {
        sendEvent(BirdDetailScreenReducer.BirdDetailScreenEvent.CloseBigImage)
    }


}