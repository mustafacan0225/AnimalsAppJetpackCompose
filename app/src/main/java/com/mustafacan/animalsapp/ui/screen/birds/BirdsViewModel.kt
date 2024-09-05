package com.mustafacan.animalsapp.ui.screen.birds

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mustafacan.domain.model.birds.Bird
import com.mustafacan.domain.model.response.ApiResponse
import com.mustafacan.domain.usecase.birds.GetBirdsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BirdsViewModel @Inject constructor(private val getBirdsUseCase: GetBirdsUseCase): ViewModel() {
    init {
        Log.d("initVM", "initVM BirdsViewModel")
        getBirds()
    }

    fun getBirds() {
        viewModelScope.launch {
            getBirdsUseCase.runUseCase { result ->
                when(result) {
                    is ApiResponse.Success<List<Bird>> -> {
                        Log.d("response", "bird count: " + result.data.size.toString())
                    }

                    is ApiResponse.Error<List<Bird>> -> {
                        Log.d("response", "birds api request error: " + result.exception.errorMessage)
                    }

                }

            }
        }
    }
}