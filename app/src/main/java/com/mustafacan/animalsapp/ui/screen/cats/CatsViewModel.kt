package com.mustafacan.animalsapp.ui.screen.cats

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mustafacan.data.local.LocalDataSource
import com.mustafacan.domain.model.cats.Cat
import com.mustafacan.domain.model.response.ApiResponse
import com.mustafacan.domain.usecase.cats.GetCatsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatsViewModel @Inject constructor(val localDataSource: LocalDataSource,
    private val getCatsUseCase: GetCatsUseCase): ViewModel() {
    init {
        Log.d("initVM", "initVM CatsViewModel")
        getCats()
    }

    fun getCats() {
        viewModelScope.launch {
            getCatsUseCase.runUseCase { result ->
                when(result) {
                    is ApiResponse.Success<List<Cat>> -> {
                        Log.d("response", "cat count: " + result.data.size.toString())
                    }

                    is ApiResponse.Error<List<Cat>> -> {
                        Log.d("response", "cats api request error: " + result.exception.errorMessage)
                    }

                    else -> {}
                }

            }
        }
    }

    fun incrementCounter() {
        localDataSource.setTest(localDataSource.getTest() + 1)
    }

    fun decrementCounter() {
        localDataSource.setTest(localDataSource.getTest() - 1)
    }

    /*var counter = localDataSource.getTestFlow()
        .stateIn(viewModelScope, SharingStarted.Lazily, 0)*/
}