package com.mustafacan.birds.feature.birds

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.mustafacan.domain.model.birds.Bird
import com.mustafacan.domain.model.response.ApiResponse
import com.mustafacan.domain.usecase.allanimals.GetAllFavoriteAnimalsUseCase
import com.mustafacan.domain.usecase.birds.api_usecase.GetBirdsUseCase
import com.mustafacan.domain.usecase.birds.api_usecase.SearchForBirdsUseCase
import com.mustafacan.domain.usecase.birds.roomdb_usecase.AddFavoriteBirdUseCase
import com.mustafacan.domain.usecase.birds.roomdb_usecase.DeleteFavoriteBirdUseCase
import com.mustafacan.domain.usecase.birds.roomdb_usecase.GetFlowFavoriteBirdsUseCase
import com.mustafacan.domain.usecase.birds.sharedpref_usecase.GetListTypeUseCase
import com.mustafacan.domain.usecase.birds.sharedpref_usecase.GetSearchTypeUseCase
import com.mustafacan.domain.usecase.birds.sharedpref_usecase.GetSettingsTypeUseCase
import com.mustafacan.domain.usecase.birds.sharedpref_usecase.SaveListTypeUseCase
import com.mustafacan.domain.usecase.birds.sharedpref_usecase.SaveSearchTypeUseCase
import com.mustafacan.domain.usecase.birds.sharedpref_usecase.SaveSettingsTypeUseCase
import com.mustafacan.domain.usecase.birds.temp.GetBirdsWithTemporaryDataUseCase
import com.mustafacan.core.model.enums.SearchType
import com.mustafacan.core.model.enums.ViewTypeForList
import com.mustafacan.core.model.enums.ViewTypeForSettings
import com.mustafacan.core.viewmodel.BaseViewModel
import com.mustafacan.birds.R
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
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
    private val getFlowFavoriteBirdsUseCase: GetFlowFavoriteBirdsUseCase,
    private val deleteFavoriteBirdUseCase: DeleteFavoriteBirdUseCase,
    private val getSearchTypeUseCase: GetSearchTypeUseCase,
    private val getListTypeUseCase: GetListTypeUseCase,
    private val getSettingsTypeUseCase: GetSettingsTypeUseCase,
    private val saveListTypeUseCase: SaveListTypeUseCase,
    private val saveSearchTypeUseCase: SaveSearchTypeUseCase,
    private val saveSettingsTypeUseCase: SaveSettingsTypeUseCase,
    private val getBirdsWithTemporaryDataUseCase: GetBirdsWithTemporaryDataUseCase,
    private val getAllFavoriteAnimalsUseCase: GetAllFavoriteAnimalsUseCase
) : BaseViewModel<BirdsScreenUiStateManager.BirdsScreenState, BirdsScreenUiStateManager.BirdsScreenEvent,
        BirdsScreenUiStateManager.BirdsScreenEffect>(
    initialState = BirdsScreenUiStateManager.BirdsScreenState.initial(),
    uiStateManager = BirdsScreenUiStateManager()
) {

    init {

        callBirds()
        listenFavoriteBirds()
        listenAllFavoriteAnimals()
    }

    fun callBirds() {
        sendEvent(BirdsScreenUiStateManager.BirdsScreenEvent.Loading)
        getBirds()
    }

    fun getBirds() {
        viewModelScope.launch {
            delay(3000)
            when (val response = getBirdsUseCase.runUseCase()) {

                is ApiResponse.Success<List<Bird>> -> {
                    sendEvent(
                        BirdsScreenUiStateManager.BirdsScreenEvent.DataReceived(
                            response.data,
                            null
                        )
                    )
                }

                is ApiResponse.Error -> {
                    sendEvent(
                        BirdsScreenUiStateManager.BirdsScreenEvent.DataReceived(
                            null, context.getString(
                                R.string.error_message
                            )
                        )
                    )
                }
            }

        }
    }

    fun getBirdsWithTempData() {
        sendEvent(BirdsScreenUiStateManager.BirdsScreenEvent.Loading)
        viewModelScope.launch {

            delay(3000)
            when (val response = getBirdsWithTemporaryDataUseCase.runUseCase()) {

                is ApiResponse.Success<List<Bird>> -> {
                    sendEvent(
                        BirdsScreenUiStateManager.BirdsScreenEvent.DataReceived(
                            response.data,
                            null
                        )
                    )
                }

                is ApiResponse.Error -> {
                    sendEvent(
                        BirdsScreenUiStateManager.BirdsScreenEvent.DataReceived(
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
            sendEvent(
                BirdsScreenUiStateManager.BirdsScreenEvent.LoadSettings(
                    searchType,
                    settingsType,
                    listType
                )
            )
        }
    }

    fun addFavoriteBird(bird: Bird) {
        viewModelScope.launch {
            if (addFavoriteBirdUseCase.runUseCase(bird)) {
                sendEvent(BirdsScreenUiStateManager.BirdsScreenEvent.UpdateBirdIsFollowed(bird))
            }
        }
    }

    fun deleteFavoriteBird(bird: Bird) {
        viewModelScope.launch {
            if (deleteFavoriteBirdUseCase.runUseCase(bird)) {
                sendEvent(
                    BirdsScreenUiStateManager.BirdsScreenEvent.UpdateBirdIsFollowed(
                        bird.copy(
                            isFavorite = false
                        )
                    )
                )
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

        sendEvent(BirdsScreenUiStateManager.BirdsScreenEvent.DataChanged(result))
    }

    fun remoteSearch(query: String) {
        if (query.isEmpty()) {
            sendEvent(BirdsScreenUiStateManager.BirdsScreenEvent.DataChanged(state.value.birdsBackup!!))
        } else {
            sendEvent(BirdsScreenUiStateManager.BirdsScreenEvent.Loading)
            viewModelScope.launch {
                //delay for test
                delay(3000)
                when (val response = searchForBirdsUseCase.runUseCase(query)) {
                    is ApiResponse.Success<List<Bird>> -> {
                        sendEvent(
                            BirdsScreenUiStateManager.BirdsScreenEvent.DataReceivedWithSearch(
                                response.data,
                                null
                            )
                        )
                    }

                    is ApiResponse.Error -> {
                        sendEvent(
                            BirdsScreenUiStateManager.BirdsScreenEvent.DataReceivedWithSearch(
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
        sendEventForEffect(BirdsScreenUiStateManager.BirdsScreenEvent.BirdDetail(bird))
    }

    fun navigateToSettings() {
        sendEvent(BirdsScreenUiStateManager.BirdsScreenEvent.OpenSettings)
    }

    fun closeSettings() {
        sendEvent(BirdsScreenUiStateManager.BirdsScreenEvent.CloseSettings)
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
            sendEvent(
                BirdsScreenUiStateManager.BirdsScreenEvent.SettingsUpdated(
                    viewTypeForList,
                    viewTypeForSettings,
                    searchType
                )
            )
        }
    }

    fun listenFavoriteBirds() {
        viewModelScope.launch {
            val favoriteBirdsFlow = getFlowFavoriteBirdsUseCase.runUseCase()
            favoriteBirdsFlow.stateIn(this).collectLatest { favoriteList ->
                sendEvent(
                    BirdsScreenUiStateManager.BirdsScreenEvent.FavoriteBirdsChanged(
                        favoriteList
                    )
                )
            }
        }
    }

    fun listenAllFavoriteAnimals() {
        viewModelScope.launch {
            val allFavoriteAnimalsFlow = getAllFavoriteAnimalsUseCase.runUseCase()
            allFavoriteAnimalsFlow.collectLatest {
                sendEvent(BirdsScreenUiStateManager.BirdsScreenEvent.AllFavoriteAnimalsChanged(it))
            }
        }
    }

    fun showBigImage(bird: Bird) {
        sendEvent(BirdsScreenUiStateManager.BirdsScreenEvent.ShowBigImage(bird))
    }

    fun closeBigImage() {
        sendEvent(BirdsScreenUiStateManager.BirdsScreenEvent.CloseBigImage)
    }

    fun showAllFavoriteAnimals() {
        sendEvent(BirdsScreenUiStateManager.BirdsScreenEvent.ShowAllFavoriteAnimals)
    }

    fun closeAllFavoriteAnimals() {
        sendEvent(BirdsScreenUiStateManager.BirdsScreenEvent.CloseAllFavoriteAnimals)
    }

}