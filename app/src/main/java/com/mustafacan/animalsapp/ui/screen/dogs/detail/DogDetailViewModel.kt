package com.mustafacan.animalsapp.ui.screen.dogs.detail

import android.content.Context
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.mustafacan.data.local.LocalDataSource
import com.mustafacan.domain.model.dogs.Dog
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class DogDetailViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    val localDataSource: LocalDataSource,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val dog: Dog? = savedStateHandle["dog"]

    init {
        Log.d("initVM", "initVM DogDetailViewModel")
    }
}