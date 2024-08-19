package com.mustafacan.animalsapp.ui.screen.cats

import android.util.Log
import androidx.lifecycle.ViewModel
import com.mustafacan.data.local.LocalDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CatsViewModel @Inject constructor(val localDataSource: LocalDataSource): ViewModel() {
    init {
        Log.d("initVM", "initVM CatsViewModel")
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