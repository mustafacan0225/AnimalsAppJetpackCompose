package com.mustafacan.ui_dogs.feature.dogdetail

import android.content.Context
import android.util.Log
import androidx.compose.foundation.pager.PagerState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.mustafacan.data.local.datasource.sharedpref.dogs.LocalDataSourceDogs
import com.mustafacan.domain.model.dogs.Dog
import com.mustafacan.domain.usecase.dogs.roomdb.AddFavoriteDogUseCase
import com.mustafacan.domain.usecase.dogs.roomdb.DeleteFavoriteDogUseCase
import com.mustafacan.ui_common.model.enums.ViewTypeForTab
import com.mustafacan.ui_common.viewmodel.BaseViewModel
import com.mustafacan.ui_dogs.R
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DogDetailViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    val localDataSource: LocalDataSourceDogs,
    private val savedStateHandle: SavedStateHandle,
    private val addFavoriteDogUseCase: AddFavoriteDogUseCase,
    private val deleteFavoriteDogUseCase: DeleteFavoriteDogUseCase
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

    fun getTemperament(dog: Dog): List<String> {

        if ((dog.temperament?: "").contains(",")) {
            var list = dog.temperament?.replace(" ", "")?.split(",")
            list?.let {
                return it
            }
        }

        return listOf(dog.temperament?: "")
    }

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

    fun updateIsFavorite() {
        viewModelScope.launch {
            if (state.value.dog?.isFavorite?: false) {
                if (deleteFavoriteDogUseCase.runUseCase(dog!!)) {
                    Log.d("room-test", "deleted favorite dog " + dog.name)
                    sendEvent(DogDetailScreenReducer.DogDetailScreenEvent.UpdateDogIsFavorite)
                }
            } else {
                if (addFavoriteDogUseCase.runUseCase(dog!!.copy(isFavorite = true))) {
                    Log.d("room-test", "added favorite dog " + dog.name)
                    sendEvent(DogDetailScreenReducer.DogDetailScreenEvent.UpdateDogIsFavorite)
                }

            }
        }
    }
}