package com.mustafacan.animalsapp.ui.screen.birds

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BirdsViewModel @Inject constructor(): ViewModel() {
    init {
        Log.d("initVM", "initVM BirdsViewModel")
    }
}