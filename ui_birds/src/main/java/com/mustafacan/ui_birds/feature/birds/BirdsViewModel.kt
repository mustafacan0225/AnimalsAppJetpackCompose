package com.mustafacan.ui_birds.feature.birds

import android.content.Context
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.mustafacan.domain.model.birds.Bird
import com.mustafacan.domain.model.dogs.Dog
import com.mustafacan.domain.model.response.ApiResponse
import com.mustafacan.domain.usecase.birds.api_usecase.GetBirdsUseCase
import com.mustafacan.domain.usecase.birds.api_usecase.SearchForBirdsUseCase
import com.mustafacan.domain.usecase.birds.roomdb_usecase.AddFavoriteBirdUseCase
import com.mustafacan.domain.usecase.birds.roomdb_usecase.DeleteFavoriteBirdUseCase
import com.mustafacan.domain.usecase.birds.roomdb_usecase.GetFavoriteBirdsUseCase
import com.mustafacan.domain.usecase.birds.sharedpref_usecase.GetListTypeUseCase
import com.mustafacan.domain.usecase.birds.sharedpref_usecase.GetSearchTypeUseCase
import com.mustafacan.domain.usecase.birds.sharedpref_usecase.GetSettingsTypeUseCase
import com.mustafacan.domain.usecase.birds.sharedpref_usecase.SaveListTypeUseCase
import com.mustafacan.domain.usecase.birds.sharedpref_usecase.SaveSearchTypeUseCase
import com.mustafacan.domain.usecase.birds.sharedpref_usecase.SaveSettingsTypeUseCase
import com.mustafacan.ui_common.model.enums.SearchType
import com.mustafacan.ui_common.model.enums.ViewTypeForList
import com.mustafacan.ui_common.model.enums.ViewTypeForSettings
import com.mustafacan.ui_common.viewmodel.BaseViewModel
import com.mustafacan.ui_birds.R
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BirdsViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val getBirdsUseCase: GetBirdsUseCase,
    private val searchForBirdsUseCase: SearchForBirdsUseCase,
    private val addFavoriteBirdUseCase: AddFavoriteBirdUseCase,
    private val getFavoriteBirdsUseCase: GetFavoriteBirdsUseCase,
    private val deleteFavoriteBirdUseCase: DeleteFavoriteBirdUseCase,
    private val getSearchTypeUseCase: GetSearchTypeUseCase,
    private val getListTypeUseCase: GetListTypeUseCase,
    private val getSettingsTypeUseCase: GetSettingsTypeUseCase,
    private val saveListTypeUseCase: SaveListTypeUseCase,
    private val saveSearchTypeUseCase: SaveSearchTypeUseCase,
    private val saveSettingsTypeUseCase: SaveSettingsTypeUseCase
) : BaseViewModel<BirdsScreenReducer.BirdsScreenState, BirdsScreenReducer.BirdsScreenEvent,
        BirdsScreenReducer.BirdsScreenEffect>(
    initialState = BirdsScreenReducer.BirdsScreenState.initial(),
    reducer = BirdsScreenReducer()
) {

    init {

        callBirds()

        viewModelScope.launch {
            val favoriteAnimalsFlow = getFavoriteBirdsUseCase.runUseCase()
            favoriteAnimalsChanged(favoriteAnimalsFlow)
        }

    }

    fun callBirds() {
        sendEvent(BirdsScreenReducer.BirdsScreenEvent.Loading)
        getBirds()
    }

    fun getBirds() {
        viewModelScope.launch {

            delay(3000)
            when (val response = getBirdsUseCase.runUseCase()) {

                is ApiResponse.Success<List<Bird>> -> {
                    sendEvent(BirdsScreenReducer.BirdsScreenEvent.DataReceived(response.data, null))
                }

                is ApiResponse.Error -> {
                    sendEvent(
                        BirdsScreenReducer.BirdsScreenEvent.DataReceived(
                            null, context.getString(
                                R.string.error_message
                            )
                        )
                    )
                }
            }

        }
    }

    fun loadSettings() {
        viewModelScope.launch {
            val searchType = getSearchTypeUseCase.runUseCase()
            val listType = getListTypeUseCase.runUseCase()
            val settingsType = getSettingsTypeUseCase.runUseCase()
            sendEvent(BirdsScreenReducer.BirdsScreenEvent.LoadSettings(searchType, settingsType, listType))
        }
    }

    fun addFavoriteBird(bird: Bird) {
        viewModelScope.launch {
            if (addFavoriteBirdUseCase.runUseCase(bird)) {
                Log.d("room-test", "added favorite bird " + bird.name)
                sendEvent(BirdsScreenReducer.BirdsScreenEvent.UpdateBirdIsFollowed(bird))
            }
        }
    }

    fun deleteFavoriteBird(bird: Bird) {
        viewModelScope.launch {
            if (deleteFavoriteBirdUseCase.runUseCase(bird)) {
                Log.d("room-test", "deleted favorite bird " + bird.name)
                sendEvent(BirdsScreenReducer.BirdsScreenEvent.UpdateBirdIsFollowed(bird.copy(isFavorite = false)))
            }
        }
    }


    fun localSearch(query: String) {
        var result: List<Bird> = listOf()
        if (query.isEmpty()) {
            result = state.value.birdsBackup!!
        } else {
            result = state.value.birdsBackup!!.filter {
                it.name?.lowercase()?.contains(query.lowercase()) ?: false
            }
        }

        sendEvent(BirdsScreenReducer.BirdsScreenEvent.DataChanged(result))
    }

    fun remoteSearch(query: String) {
        if (query.isEmpty()) {
            sendEvent(BirdsScreenReducer.BirdsScreenEvent.DataChanged(state.value.birdsBackup!!))
        } else {
            sendEvent(BirdsScreenReducer.BirdsScreenEvent.Loading)
            viewModelScope.launch {
                //delay for test
                delay(3000)
                when (val response = searchForBirdsUseCase.runUseCase(query)) {
                    is ApiResponse.Success<List<Bird>> -> {
                        sendEvent(
                            BirdsScreenReducer.BirdsScreenEvent.DataReceivedWithSearch(
                                response.data,
                                null
                            )
                        )
                    }

                    is ApiResponse.Error -> {
                        sendEvent(
                            BirdsScreenReducer.BirdsScreenEvent.DataReceivedWithSearch(
                                null, context.getString(
                                    R.string.error_message
                                )
                            )
                        )
                    }
                }

            }
        }

    }

    fun navigateToBirdDetail(bird: Bird) {
        sendEventForEffect(BirdsScreenReducer.BirdsScreenEvent.BirdDetail(bird))
    }

    fun navigateToSettings() {
        sendEvent(BirdsScreenReducer.BirdsScreenEvent.OpenSettings)
    }

    fun closeSettings() {
        sendEvent(BirdsScreenReducer.BirdsScreenEvent.CloseSettings)
    }

    fun settingsUpdated(
        viewTypeForList: ViewTypeForList,
        viewTypeForSettings: ViewTypeForSettings,
        searchType: SearchType
    ) {
        viewModelScope.launch {
            saveListTypeUseCase.runUseCase(viewTypeForList.name)
            saveSettingsTypeUseCase.runUseCase(viewTypeForSettings.name)
            saveSearchTypeUseCase.runUseCase(searchType.name)
            sendEvent(BirdsScreenReducer.BirdsScreenEvent.SettingsUpdated(viewTypeForList, viewTypeForSettings, searchType))
        }
    }

    fun favoriteAnimalsChanged(favoriteAnimalsFlow: Flow<List<Bird>>) {
        viewModelScope.launch {
            favoriteAnimalsFlow.stateIn(this).collectLatest { favoriteList ->
                Log.d("room-test", "favorite list count: " + favoriteList.size.toString())
                sendEvent(BirdsScreenReducer.BirdsScreenEvent.FavoriteAnimalCountChanged(favoriteList))
            }
        }
    }

    fun showBigImage(bird: Bird) {
        sendEvent(BirdsScreenReducer.BirdsScreenEvent.ShowBigImage(bird))
    }

    fun closeBigImage() {
        sendEvent(BirdsScreenReducer.BirdsScreenEvent.CloseBigImage)
    }

}