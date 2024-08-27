package com.mustafacan.animalsapp.ui.screen.dogs

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mustafacan.animalsapp.R
import com.mustafacan.animalsapp.ui.base.BaseViewModel
import com.mustafacan.data.local.LocalDataSource
import com.mustafacan.domain.model.birds.Bird
import com.mustafacan.domain.model.dogs.Dog
import com.mustafacan.domain.model.response.ApiResponse
import com.mustafacan.domain.usecase.dogs.GetDogsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DogsViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    val localDataSource: LocalDataSource,
    private val getDogsUseCase: GetDogsUseCase
) : BaseViewModel<DogsScreenReducer.DogsScreenState, DogsScreenReducer.DogsScreenEvent,
        DogsScreenReducer.DogsScreenEffect>(initialState = DogsScreenReducer.DogsScreenState.initial(),
            reducer = DogsScreenReducer()) {
    init {
        Log.d("initVM", "initVM DogsViewModel")
        callDogs()
    }

    fun callDogs() {
        sendEvent(DogsScreenReducer.DogsScreenEvent.Loading)
        getDogs()
    }

    fun getDogs() {
        viewModelScope.launch {
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

                    else -> {}
                }

            }
        }
    }
}