package com.mustafacan.dogs.feature.dogs

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.mustafacan.domain.model.dogs.Dog
import com.mustafacan.domain.model.response.ApiResponse
import com.mustafacan.domain.usecase.allanimals.GetAllFavoriteAnimalsUseCase
import com.mustafacan.domain.usecase.dogs.roomdb.AddFavoriteDogUseCase
import com.mustafacan.domain.usecase.dogs.api.GetDogsUseCase
import com.mustafacan.domain.usecase.dogs.temp.GetDogsWithTemporaryDataUseCase
import com.mustafacan.domain.usecase.dogs.roomdb.GetFlowFavoriteDogsUseCase
import com.mustafacan.domain.usecase.dogs.api.SearchForDogsUseCase
import com.mustafacan.domain.usecase.dogs.roomdb.DeleteFavoriteDogUseCase
import com.mustafacan.domain.usecase.dogs.sharedpreference.GetListTypeUseCase
import com.mustafacan.domain.usecase.dogs.sharedpreference.GetSearchTypeUseCase
import com.mustafacan.domain.usecase.dogs.sharedpreference.GetSettingsTypeUseCase
import com.mustafacan.domain.usecase.dogs.sharedpreference.SaveListTypeUseCase
import com.mustafacan.domain.usecase.dogs.sharedpreference.SaveSearchTypeUseCase
import com.mustafacan.domain.usecase.dogs.sharedpreference.SaveSettingsTypeUseCase
import com.mustafacan.core.model.enums.SearchType
import com.mustafacan.core.model.enums.ViewTypeForList
import com.mustafacan.core.model.enums.ViewTypeForSettings
import com.mustafacan.core.viewmodel.BaseViewModel
import com.mustafacan.dogs.R
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DogsViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val getDogsUseCase: GetDogsUseCase,
    private val searchForDogsUseCase: SearchForDogsUseCase,
    private val addFavoriteDogUseCase: AddFavoriteDogUseCase,
    private val getFlowFavoriteDogsUseCase: GetFlowFavoriteDogsUseCase,
    private val deleteFavoriteDogUseCase: DeleteFavoriteDogUseCase,
    private val getSearchTypeUseCase: GetSearchTypeUseCase,
    private val getListTypeUseCase: GetListTypeUseCase,
    private val getSettingsTypeUseCase: GetSettingsTypeUseCase,
    private val saveListTypeUseCase: SaveListTypeUseCase,
    private val saveSearchTypeUseCase: SaveSearchTypeUseCase,
    private val saveSettingsTypeUseCase: SaveSettingsTypeUseCase,
    private val getDogsWithTemporaryDataUseCase: GetDogsWithTemporaryDataUseCase,
    private val getAllFavoriteAnimalsUseCase: GetAllFavoriteAnimalsUseCase
) : BaseViewModel<DogsScreenUiStateManager.DogsScreenState, DogsScreenUiStateManager.DogsScreenEvent,
        DogsScreenUiStateManager.DogsScreenEffect>(
    initialState = DogsScreenUiStateManager.DogsScreenState.initial(),
    uiStateManager = DogsScreenUiStateManager()
) {

    init {

        callDogs()
        listenFavoriteDogs()
        listenAllFavoriteAnimals()

    }

    fun callDogs() {
        sendEvent(DogsScreenUiStateManager.DogsScreenEvent.Loading)
        getDogs()
    }

    fun getDogs() {
        viewModelScope.launch {

            delay(3000)
            when (val response = getDogsUseCase.runUseCase()) {

                is ApiResponse.Success<List<Dog>> -> {
                    sendEvent(
                        DogsScreenUiStateManager.DogsScreenEvent.DataReceived(
                            response.data,
                            null
                        )
                    )
                }

                is ApiResponse.Error -> {
                    sendEvent(
                        DogsScreenUiStateManager.DogsScreenEvent.DataReceived(
                            null, context.getString(
                                R.string.error_message
                            )
                        )
                    )
                }
            }

        }
    }

    fun getDogsWithTemporaryData() {
        sendEvent(DogsScreenUiStateManager.DogsScreenEvent.Loading)
        viewModelScope.launch {
            delay(3000)
            when (val response = getDogsWithTemporaryDataUseCase.runUseCase()) {

                is ApiResponse.Success<List<Dog>> -> {
                    sendEvent(
                        DogsScreenUiStateManager.DogsScreenEvent.DataReceived(
                            response.data,
                            null
                        )
                    )
                }

                is ApiResponse.Error -> {
                    sendEvent(
                        DogsScreenUiStateManager.DogsScreenEvent.DataReceived(
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
                DogsScreenUiStateManager.DogsScreenEvent.LoadSettings(
                    searchType,
                    settingsType,
                    listType
                )
            )
        }
    }

    fun addFavoriteDog(dog: Dog) {
        viewModelScope.launch {
            if (addFavoriteDogUseCase.runUseCase(dog)) {
                sendEvent(DogsScreenUiStateManager.DogsScreenEvent.UpdateDogIsFollowed(dog))
            }
        }
    }

    fun deleteFavoriteDog(dog: Dog) {
        viewModelScope.launch {
            if (deleteFavoriteDogUseCase.runUseCase(dog)) {
                sendEvent(
                    DogsScreenUiStateManager.DogsScreenEvent.UpdateDogIsFollowed(
                        dog.copy(
                            isFavorite = false
                        )
                    )
                )
            }
        }
    }


    fun localSearch(query: String) {
        var result: List<Dog> = listOf()
        if (query.isEmpty()) {
            result = state.value.dogsBackup!!
        } else {
            result = state.value.dogsBackup!!.filter {
                it.name?.lowercase()?.contains(query.lowercase()) ?: false
                        || it.origin?.lowercase()?.contains(query.lowercase()) ?: false
            }
        }

        sendEvent(DogsScreenUiStateManager.DogsScreenEvent.DataChanged(result))
    }

    fun remoteSearch(query: String) {
        if (query.isEmpty()) {
            sendEvent(DogsScreenUiStateManager.DogsScreenEvent.DataChanged(state.value.dogsBackup!!))
        } else {
            sendEvent(DogsScreenUiStateManager.DogsScreenEvent.Loading)
            viewModelScope.launch {
                //delay for test
                delay(3000)
                when (val response = searchForDogsUseCase.runUseCase(query)) {
                    is ApiResponse.Success<List<Dog>> -> {
                        sendEvent(
                            DogsScreenUiStateManager.DogsScreenEvent.DataReceivedWithSearch(
                                response.data,
                                null
                            )
                        )
                    }

                    is ApiResponse.Error -> {
                        sendEvent(
                            DogsScreenUiStateManager.DogsScreenEvent.DataReceivedWithSearch(
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

    fun navigateToDogDetail(dog: Dog) {
        sendEventForEffect(DogsScreenUiStateManager.DogsScreenEvent.DogDetail(dog))
    }

    fun navigateToSettings() {
        sendEvent(DogsScreenUiStateManager.DogsScreenEvent.OpenSettings)
    }

    fun closeSettings() {
        sendEvent(DogsScreenUiStateManager.DogsScreenEvent.CloseSettings)
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
                DogsScreenUiStateManager.DogsScreenEvent.SettingsUpdated(
                    viewTypeForList,
                    viewTypeForSettings,
                    searchType
                )
            )
        }
    }

    fun listenFavoriteDogs() {
        viewModelScope.launch {
            val favoriteDogs = getFlowFavoriteDogsUseCase.runUseCase()
            favoriteDogs.stateIn(this).collectLatest { favoriteList ->
                sendEvent(DogsScreenUiStateManager.DogsScreenEvent.FavoriteDogsChanged(favoriteList))
            }
        }
    }

    fun listenAllFavoriteAnimals() {
        viewModelScope.launch {
            val allFavoriteAnimalsFlow = getAllFavoriteAnimalsUseCase.runUseCase()
            allFavoriteAnimalsFlow.collectLatest {
                sendEvent(DogsScreenUiStateManager.DogsScreenEvent.AllFavoriteAnimalsChanged(it))
            }
        }
    }

    fun showBigImage(dog: Dog) {
        sendEvent(DogsScreenUiStateManager.DogsScreenEvent.ShowBigImage(dog))
    }

    fun closeBigImage() {
        sendEvent(DogsScreenUiStateManager.DogsScreenEvent.CloseBigImage)
    }

    fun showAllFavoriteAnimals() {
        sendEvent(DogsScreenUiStateManager.DogsScreenEvent.ShowAllFavoriteAnimals)
    }

    fun closeAllFavoriteAnimals() {
        sendEvent(DogsScreenUiStateManager.DogsScreenEvent.CloseAllFavoriteAnimals)
    }


}