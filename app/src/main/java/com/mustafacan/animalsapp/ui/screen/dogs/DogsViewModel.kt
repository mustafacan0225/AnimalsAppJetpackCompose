package com.mustafacan.animalsapp.ui.screen.dogs

import android.content.Context
import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewModelScope
import com.mustafacan.animalsapp.R
import com.mustafacan.animalsapp.ui.base.BaseViewModel
import com.mustafacan.animalsapp.ui.model.enums.ViewTypeForList
import com.mustafacan.animalsapp.ui.model.enums.ViewTypeForSettings
import com.mustafacan.data.local.LocalDataSource
import com.mustafacan.domain.model.dogs.Dog
import com.mustafacan.domain.model.response.ApiResponse
import com.mustafacan.domain.usecase.dogs.GetDogsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DogsViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val localDataSource: LocalDataSource,
    private val getDogsUseCase: GetDogsUseCase
) : BaseViewModel<DogsScreenReducer.DogsScreenState, DogsScreenReducer.DogsScreenEvent,
        DogsScreenReducer.DogsScreenEffect>(initialState = DogsScreenReducer.DogsScreenState.initial(localDataSource),
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

    fun navigateToDogDetail(dog: Dog) {
        sendEventForEffect(DogsScreenReducer.DogsScreenEvent.DogDetail(dog))
    }

    fun navigateToSettings() {
        sendEvent(DogsScreenReducer.DogsScreenEvent.OpenSettings)
    }

    fun closeSettings() {
        sendEvent(DogsScreenReducer.DogsScreenEvent.CloseSettings)
    }

    fun settingsUpdated(viewTypeForList: ViewTypeForList, viewTypeForSettings: ViewTypeForSettings) {
        sendEvent(DogsScreenReducer.DogsScreenEvent.SettingsUpdated(viewTypeForList, viewTypeForSettings))
    }
}