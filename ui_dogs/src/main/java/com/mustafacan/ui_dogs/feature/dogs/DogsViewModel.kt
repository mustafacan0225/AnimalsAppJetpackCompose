package com.mustafacan.ui_dogs.feature.dogs

import android.content.Context
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.mustafacan.data.local.LocalDataSource
import com.mustafacan.data.local.datasource.sharedpref.dogs.LocalDataSourceDogs
import com.mustafacan.domain.model.dogs.Dog
import com.mustafacan.domain.model.response.ApiResponse
import com.mustafacan.domain.usecase.dogs.roomdb.AddFavoriteDogUseCase
import com.mustafacan.domain.usecase.dogs.GetDogsUseCase
import com.mustafacan.domain.usecase.dogs.roomdb.GetFavoriteDogsUseCase
import com.mustafacan.domain.usecase.dogs.SearchForDogsUseCase
import com.mustafacan.domain.usecase.dogs.roomdb.DeleteFavoriteDogUseCase
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
    private val localDataSource: LocalDataSource,
    private val localDataSourceDogs: LocalDataSourceDogs,
    private val getDogsUseCase: GetDogsUseCase,
    private val searchForDogsUseCase: SearchForDogsUseCase,
    private val addFavoriteDogUseCase: AddFavoriteDogUseCase,
    private val getFavoriteDogsUseCase: GetFavoriteDogsUseCase,
    private val deleteFavoriteDogUseCase: DeleteFavoriteDogUseCase
) : BaseViewModel<DogsScreenReducer.DogsScreenState, DogsScreenReducer.DogsScreenEvent,
        DogsScreenReducer.DogsScreenEffect>(initialState = DogsScreenReducer.DogsScreenState.initial(localDataSource, localDataSourceDogs),
    reducer = DogsScreenReducer()) {
    init {
        Log.d("initVM", "initVM DogsViewModel")

        callDogs()

        viewModelScope.launch {
            localDataSource.getTestFlow().stateIn(this).collectLatest { newValue ->
                Log.d("listenpref:", "new value - " + newValue.toString())
                state.value.testValue.collect() {
                    Log.d("listenpref2:", "state value - " + it.toString())
                }
            }
        }

        viewModelScope.launch {
            val favoriteAnimalsFlow = getFavoriteDogsUseCase.runUseCase()
            favoriteAnimalsChanged(favoriteAnimalsFlow)
            /*getFavoriteDogsUseCase.runUseCase { favoriteAnimalsFlow ->
                favoriteAnimalsChanged(favoriteAnimalsFlow)
            }*/
        }

    }

    fun callDogs() {
        sendEvent(DogsScreenReducer.DogsScreenEvent.Loading)
        getDogs()
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

    fun getFavoriteDog() {

    }

    fun getDogs() {
        viewModelScope.launch {
            //delay for test
            delay(3000)
            when(val response = getDogsUseCase.runUseCase()) {
                is ApiResponse.Success<List<Dog>> -> {
                    sendEvent(DogsScreenReducer.DogsScreenEvent.DataReceived(response.data, null))
                }

                is ApiResponse.Error<List<Dog>> -> {
                    sendEvent(DogsScreenReducer.DogsScreenEvent.DataReceived(null, context.getString(
                        R.string.error_message)))
                }
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
                when(val response = searchForDogsUseCase.runUseCase(query)) {
                    is ApiResponse.Success<List<Dog>> -> {
                        sendEvent(DogsScreenReducer.DogsScreenEvent.DataReceivedWithSearch(response.data, null))
                    }

                    is ApiResponse.Error<List<Dog>> -> {
                        sendEvent(DogsScreenReducer.DogsScreenEvent.DataReceivedWithSearch(null, context.getString(
                            R.string.error_message)))
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

    fun settingsUpdated(viewTypeForList: ViewTypeForList, viewTypeForSettings: ViewTypeForSettings, searchType: SearchType) {
        localDataSourceDogs.saveListTypeForDogList(viewTypeForList.name)
        localDataSourceDogs.saveSettingsTypeForDogList(viewTypeForSettings.name)
        localDataSourceDogs.saveSearchTypeForDogList(searchType.name)
        sendEvent(DogsScreenReducer.DogsScreenEvent.SettingsUpdated(viewTypeForList, viewTypeForSettings, searchType))
    }

    fun favoriteAnimalsChanged(favoriteAnimalsFlow: Flow<List<Dog>>) {
        viewModelScope.launch {
            favoriteAnimalsFlow.stateIn(this).collectLatest { favoriteList ->
                Log.d("room-test", "favorite list count: " + favoriteList.size.toString())
                sendEvent(DogsScreenReducer.DogsScreenEvent.FavoriteAnimalCountChanged(favoriteList.size))
            }
        }
    }
}