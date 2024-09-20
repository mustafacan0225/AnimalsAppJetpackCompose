package com.mustafacan.ui_dogs.feature.dogdetail

import android.content.Context
import android.util.Log
import androidx.compose.foundation.pager.PagerState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.mustafacan.data.local.LocalDataSource
import com.mustafacan.data.local.datasource.sharedpref.dogs.LocalDataSourceDogs
import com.mustafacan.domain.model.dogs.Dog
import com.mustafacan.ui_common.model.enums.SearchType
import com.mustafacan.ui_common.model.enums.ViewTypeForList
import com.mustafacan.ui_common.model.enums.ViewTypeForSettings
import com.mustafacan.ui_common.model.enums.ViewTypeForTab
import com.mustafacan.ui_common.viewmodel.BaseViewModel
import com.mustafacan.ui_dogs.R
import com.mustafacan.ui_dogs.feature.dogs.DogsScreenReducer
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

@HiltViewModel
class DogDetailViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    val localDataSource: LocalDataSourceDogs,
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel<DogDetailScreenReducer.DogDetailScreenState, DogDetailScreenReducer.DogDetailScreenEvent,
        DogDetailScreenReducer.DogDetailScreenEffect>(
    initialState = DogDetailScreenReducer.DogDetailScreenState.initial(localDataSource),
    reducer = DogDetailScreenReducer()
) {

    val dog: Dog? = savedStateHandle["dog"]

    val tabList = listOf(
        R.string.tab_general to R.drawable.general,
        R.string.tab_info to R.drawable.dog,
        R.string.tab_temperament to R.drawable.temperament,
        R.string.tab_colors to R.drawable.colors
    )

    fun load(pagerState: PagerState, scope: CoroutineScope) {
        sendEvent(DogDetailScreenReducer.DogDetailScreenEvent.Load(dog = dog!!, pagerState = pagerState, tabList = tabList, scope = scope))
    }

    fun onClickTab(index: Int) {
        sendEvent(DogDetailScreenReducer.DogDetailScreenEvent.OnClickTabItem(index))
    }

    fun navigateToSettings() {
        sendEvent(DogDetailScreenReducer.DogDetailScreenEvent.OpenSettings)
    }

    fun closeSettings() {
        sendEvent(DogDetailScreenReducer.DogDetailScreenEvent.CloseSettings)
    }

    fun settingsUpdated(viewTypeForTab: ViewTypeForTab) {
        localDataSource.saveTabTypeForDogDetail(viewTypeForTab.name)
        sendEvent(DogDetailScreenReducer.DogDetailScreenEvent.SettingsUpdated(viewTypeForTab))
    }
}