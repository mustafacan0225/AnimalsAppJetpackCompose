package com.mustafacan.animalsapp.ui.screen.dogs

import android.content.Context
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.mustafacan.animalsapp.R
import com.mustafacan.animalsapp.ui.base.BaseViewModel
import com.mustafacan.animalsapp.ui.model.enums.SearchType
import com.mustafacan.animalsapp.ui.model.enums.ViewTypeForList
import com.mustafacan.animalsapp.ui.model.enums.ViewTypeForSettings
import com.mustafacan.data.local.LocalDataSource
import com.mustafacan.data.local.datasource.sharedpref.dogs.LocalDataSourceDogs
import com.mustafacan.domain.model.dogs.Dog
import com.mustafacan.domain.model.response.ApiResponse
import com.mustafacan.domain.usecase.dogs.GetDogsUseCase
import com.mustafacan.domain.usecase.dogs.SearchForDogsUseCase
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
    private val localDataSource: LocalDataSource,
    private val localDataSourceDogs: LocalDataSourceDogs,
    private val getDogsUseCase: GetDogsUseCase,
    private val searchForDogsUseCase: SearchForDogsUseCase
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

    }


    fun callDogs() {
        sendEvent(DogsScreenReducer.DogsScreenEvent.Loading)
        getDogs()
    }

    fun getDogs() {
        viewModelScope.launch {
            //delay for test
            delay(3000)

            getDogsUseCase.runUseCase { result ->
                when (result) {
                    is ApiResponse.Success<List<Dog>> -> {
                        sendEvent(DogsScreenReducer.DogsScreenEvent.DataReceived(result.data, null))
                    }

                    is ApiResponse.Error<List<Dog>> -> {
                        sendEvent(DogsScreenReducer.DogsScreenEvent.DataReceived(null, context.getString(
                            R.string.error_message)))
                    }
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

                searchForDogsUseCase.runUseCase(query) { result ->
                    when (result) {
                        is ApiResponse.Success<List<Dog>> -> {
                            sendEvent(DogsScreenReducer.DogsScreenEvent.DataReceivedWithSearch(result.data, null))
                        }

                        is ApiResponse.Error<List<Dog>> -> {
                            sendEvent(DogsScreenReducer.DogsScreenEvent.DataReceivedWithSearch(null, context.getString(
                                R.string.error_message)))
                        }
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
}