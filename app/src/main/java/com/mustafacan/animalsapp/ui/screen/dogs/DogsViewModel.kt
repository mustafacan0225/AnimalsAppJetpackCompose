package com.mustafacan.animalsapp.ui.screen.dogs

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.mustafacan.data.local.LocalDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class DogsViewModel @Inject constructor(@ApplicationContext private val context: Context,
                                        val localDataSource: LocalDataSource
): ViewModel() {
    init {
        Log.d("initVM", "initVM DogsViewModel")
    }
}