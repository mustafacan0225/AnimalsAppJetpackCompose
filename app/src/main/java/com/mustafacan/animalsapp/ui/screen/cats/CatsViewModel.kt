package com.mustafacan.animalsapp.ui.screen.cats

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class CatsViewModel @Inject constructor(): ViewModel() {
    init {
        Log.d("initVM", "initVM CatsViewModel")

    }

}