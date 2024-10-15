package com.mustafacan.ui_dogs.feature.dogs

import android.content.Context
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.mustafacan.domain.model.dogs.Dog
import com.mustafacan.domain.model.response.ApiResponse
import com.mustafacan.domain.usecase.dogs.roomdb_usecase.AddFavoriteDogUseCase
import com.mustafacan.domain.usecase.dogs.api_usecase.GetDogsUseCase
import com.mustafacan.domain.usecase.dogs.temp.GetDogsWithTemporaryDataUseCase
import com.mustafacan.domain.usecase.dogs.roomdb_usecase.GetFavoriteDogsUseCase
import com.mustafacan.domain.usecase.dogs.api_usecase.SearchForDogsUseCase
import com.mustafacan.domain.usecase.dogs.roomdb_usecase.DeleteFavoriteDogUseCase
import com.mustafacan.domain.usecase.dogs.sharedpref_usecase.GetListTypeUseCase
import com.mustafacan.domain.usecase.dogs.sharedpref_usecase.GetSearchTypeUseCase
import com.mustafacan.domain.usecase.dogs.sharedpref_usecase.GetSettingsTypeUseCase
import com.mustafacan.domain.usecase.dogs.sharedpref_usecase.SaveListTypeUseCase
import com.mustafacan.domain.usecase.dogs.sharedpref_usecase.SaveSearchTypeUseCase
import com.mustafacan.domain.usecase.dogs.sharedpref_usecase.SaveSettingsTypeUseCase
import com.mustafacan.ui_common.model.enums.SearchType
import com.mustafacan.ui_common.model.enums.ViewTypeForList
import com.mustafacan.ui_common.model.enums.ViewTypeForSettings
import com.mustafacan.ui_common.viewmodel.BaseViewModel
import com.mustafacan.ui_dogs.R
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
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
    private val getFavoriteDogsUseCase: GetFavoriteDogsUseCase,
    private val deleteFavoriteDogUseCase: DeleteFavoriteDogUseCase,
    private val getSearchTypeUseCase: GetSearchTypeUseCase,
    private val getListTypeUseCase: GetListTypeUseCase,
    private val getSettingsTypeUseCase: GetSettingsTypeUseCase,
    private val saveListTypeUseCase: SaveListTypeUseCase,
    private val saveSearchTypeUseCase: SaveSearchTypeUseCase,
    private val saveSettingsTypeUseCase: SaveSettingsTypeUseCase,
    private val getDogsWithTemporaryDataUseCase: GetDogsWithTemporaryDataUseCase,
) : BaseViewModel<DogsScreenReducer.DogsScreenState, DogsScreenReducer.DogsScreenEvent,
        DogsScreenReducer.DogsScreenEffect>(
    initialState = DogsScreenReducer.DogsScreenState.initial(),
    reducer = DogsScreenReducer()
) {

    init {

        callDogs()

        viewModelScope.launch {
            val favoriteAnimalsFlow = getFavoriteDogsUseCase.runUseCase()
            favoriteAnimalsChanged(favoriteAnimalsFlow)
        }

    }

    fun callDogs() {
        sendEvent(DogsScreenReducer.DogsScreenEvent.Loading)
        getDogs()
    }

    fun getDogs() {
        viewModelScope.launch {

            delay(3000)
            when (val response = getDogsUseCase.runUseCase()) {

                is ApiResponse.Success<List<Dog>> -> {
                    sendEvent(DogsScreenReducer.DogsScreenEvent.DataReceived(response.data, null))
                }

                is ApiResponse.Error -> {
                    sendEvent(
                        DogsScreenReducer.DogsScreenEvent.DataReceived(
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
        sendEvent(DogsScreenReducer.DogsScreenEvent.Loading)
        viewModelScope.launch {
            delay(3000)
            when (val response = getDogsWithTemporaryDataUseCase.runUseCase()) {

                is ApiResponse.Success<List<Dog>> -> {
                    sendEvent(DogsScreenReducer.DogsScreenEvent.DataReceived(response.data, null))
                }

                is ApiResponse.Error -> {
                    sendEvent(
                        DogsScreenReducer.DogsScreenEvent.DataReceived(
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
            sendEvent(DogsScreenReducer.DogsScreenEvent.LoadSettings(searchType, settingsType, listType))
        }
    }

    fun addFavoriteDog(dog: Dog) {
        viewModelScope.launch {
            if (addFavoriteDogUseCase.runUseCase(dog)) {
                Log.d("room-test", "added favorite dog " + dog.name)
                sendEvent(DogsScreenReducer.DogsScreenEvent.UpdateDogIsFollowed(dog))
            }
        }
    }

    fun deleteFavoriteDog(dog: Dog) {
        viewModelScope.launch {
            if (deleteFavoriteDogUseCase.runUseCase(dog)) {
                Log.d("room-test", "deleted favorite dog " + dog.name)
                sendEvent(DogsScreenReducer.DogsScreenEvent.UpdateDogIsFollowed(dog.copy(isFavorite = false)))
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

        sendEvent(DogsScreenReducer.DogsScreenEvent.DataChanged(result))
    }

    fun remoteSearch(query: String) {
        if (query.isEmpty()) {
            sendEvent(DogsScreenReducer.DogsScreenEvent.DataChanged(state.value.dogsBackup!!))
        } else {
            sendEvent(DogsScreenReducer.DogsScreenEvent.Loading)
            viewModelScope.launch {
                //delay for test
                delay(3000)
                when (val response = searchForDogsUseCase.runUseCase(query)) {
                    is ApiResponse.Success<List<Dog>> -> {
                        sendEvent(
                            DogsScreenReducer.DogsScreenEvent.DataReceivedWithSearch(
                                response.data,
                                null
                            )
                        )
                    }

                    is ApiResponse.Error -> {
                        sendEvent(
                            DogsScreenReducer.DogsScreenEvent.DataReceivedWithSearch(
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
        sendEventForEffect(DogsScreenReducer.DogsScreenEvent.DogDetail(dog))
    }

    fun navigateToSettings() {
        sendEvent(DogsScreenReducer.DogsScreenEvent.OpenSettings)
    }

    fun closeSettings() {
        sendEvent(DogsScreenReducer.DogsScreenEvent.CloseSettings)
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
            sendEvent(DogsScreenReducer.DogsScreenEvent.SettingsUpdated(viewTypeForList, viewTypeForSettings, searchType))
        }
    }

    fun favoriteAnimalsChanged(favoriteAnimalsFlow: Flow<List<Dog>>) {
        viewModelScope.launch {
            favoriteAnimalsFlow.stateIn(this).collectLatest { favoriteList ->
                Log.d("room-test", "favorite list count: " + favoriteList.size.toString())
                sendEvent(DogsScreenReducer.DogsScreenEvent.FavoriteAnimalCountChanged(favoriteList))
            }
        }
    }

    fun showBigImage(dog: Dog) {
        sendEvent(DogsScreenReducer.DogsScreenEvent.ShowBigImage(dog))
    }

    fun closeBigImage() {
        sendEvent(DogsScreenReducer.DogsScreenEvent.CloseBigImage)
    }
}