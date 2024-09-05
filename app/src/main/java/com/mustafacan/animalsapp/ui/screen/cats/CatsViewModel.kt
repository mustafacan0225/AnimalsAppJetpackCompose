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
import kotlin.random.Random

@HiltViewModel
class CatsViewModel @Inject constructor(val localDataSource: LocalDataSource,
    private val getCatsUseCase: GetCatsUseCase): ViewModel() {
    init {
        Log.d("initVM", "initVM CatsViewModel")
        getCats()
        getLocalCats()
    }

    var catList: List<Cat> = listOf()

    fun getLocalCats() {
        localDataSource.getCat()?.let {
            Log.d("localcat:", it.name + " - " + it.origin)
        }
    }

    fun getCats() {
        viewModelScope.launch {
            getCatsUseCase.runUseCase { result ->
                when(result) {
                    is ApiResponse.Success<List<Cat>> -> {
                        catList = result.data
                        Log.d("response", "cat count: " + result.data.size.toString())
                        localDataSource.saveCat(result.data.get(2))
                    }

                    is ApiResponse.Error<List<Cat>> -> {
                        Log.d("response", "cats api request error: " + result.exception.errorMessage)
                    }

                }

            }
        }
    }

    fun incrementCounter() {
        localDataSource.setTest(localDataSource.getTest() + 1)
        localDataSource.addCat(catList.get(Random.nextInt(0,30)))
    }

    fun decrementCounter() {
        localDataSource.setTest(localDataSource.getTest() - 1)
        println(localDataSource.getCats())
    }

    /*var counter = localDataSource.getTestFlow()
        .stateIn(viewModelScope, SharingStarted.Lazily, 0)*/
}