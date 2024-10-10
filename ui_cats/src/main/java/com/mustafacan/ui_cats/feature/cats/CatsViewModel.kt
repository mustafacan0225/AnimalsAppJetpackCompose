package com.mustafacan.ui_cats.feature.cats

import android.content.Context
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.mustafacan.domain.model.cats.Cat
import com.mustafacan.domain.model.response.ApiResponse
import com.mustafacan.domain.usecase.cats.api_usecase.GetCatsUseCase
import com.mustafacan.domain.usecase.cats.api_usecase.GetCatsWithMockDataUseCase
import com.mustafacan.domain.usecase.cats.api_usecase.SearchForCatsUseCase
import com.mustafacan.domain.usecase.cats.roomdb_usecase.AddFavoriteCatUseCase
import com.mustafacan.domain.usecase.cats.roomdb_usecase.DeleteFavoriteCatUseCase
import com.mustafacan.domain.usecase.cats.roomdb_usecase.GetFavoriteCatsUseCase
import com.mustafacan.domain.usecase.cats.sharedpref_usecase.GetListTypeUseCase
import com.mustafacan.domain.usecase.cats.sharedpref_usecase.GetSearchTypeUseCase
import com.mustafacan.domain.usecase.cats.sharedpref_usecase.GetSettingsTypeUseCase
import com.mustafacan.domain.usecase.cats.sharedpref_usecase.SaveListTypeUseCase
import com.mustafacan.domain.usecase.cats.sharedpref_usecase.SaveSearchTypeUseCase
import com.mustafacan.domain.usecase.cats.sharedpref_usecase.SaveSettingsTypeUseCase
import com.mustafacan.ui_common.model.enums.SearchType
import com.mustafacan.ui_common.model.enums.ViewTypeForList
import com.mustafacan.ui_common.model.enums.ViewTypeForSettings
import com.mustafacan.ui_common.viewmodel.BaseViewModel
import com.mustafacan.ui_cats.R
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatsViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val getCatsUseCase: GetCatsUseCase,
    private val searchForCatsUseCase: SearchForCatsUseCase,
    private val addFavoriteCatUseCase: AddFavoriteCatUseCase,
    private val getFavoriteCatsUseCase: GetFavoriteCatsUseCase,
    private val deleteFavoriteCatUseCase: DeleteFavoriteCatUseCase,
    private val getSearchTypeUseCase: GetSearchTypeUseCase,
    private val getListTypeUseCase: GetListTypeUseCase,
    private val getSettingsTypeUseCase: GetSettingsTypeUseCase,
    private val saveListTypeUseCase: SaveListTypeUseCase,
    private val saveSearchTypeUseCase: SaveSearchTypeUseCase,
    private val saveSettingsTypeUseCase: SaveSettingsTypeUseCase,
    private val getCatsWithMockDataUseCase: GetCatsWithMockDataUseCase
) : BaseViewModel<CatsScreenReducer.CatsScreenState, CatsScreenReducer.CatsScreenEvent,
        CatsScreenReducer.CatsScreenEffect>(
    initialState = CatsScreenReducer.CatsScreenState.initial(),
    reducer = CatsScreenReducer()
) {

    init {

        callCats()

        viewModelScope.launch {
            val favoriteAnimalsFlow = getFavoriteCatsUseCase.runUseCase()
            favoriteAnimalsChanged(favoriteAnimalsFlow)
        }

    }

    fun callCats() {
        sendEvent(CatsScreenReducer.CatsScreenEvent.Loading)
        getCats()
    }

    fun getCats() {
        viewModelScope.launch {

            delay(3000)
            when (val response = getCatsUseCase.runUseCase()) {

                is ApiResponse.Success<List<Cat>> -> {
                    sendEvent(CatsScreenReducer.CatsScreenEvent.DataReceived(response.data, null))
                }

                is ApiResponse.Error -> {
                    sendEvent(
                        CatsScreenReducer.CatsScreenEvent.DataReceived(
                            null, context.getString(
                                R.string.error_message
                            )
                        )
                    )
                }
            }

        }
    }

    fun getCatsWithMockData() {
        sendEvent(CatsScreenReducer.CatsScreenEvent.Loading)
        viewModelScope.launch {
            delay(3000)
            when (val response = getCatsWithMockDataUseCase.runUseCase()) {

                is ApiResponse.Success<List<Cat>> -> {
                    sendEvent(CatsScreenReducer.CatsScreenEvent.DataReceived(response.data, null))
                }

                is ApiResponse.Error -> {
                    sendEvent(
                        CatsScreenReducer.CatsScreenEvent.DataReceived(
                            null, context.getString(
                                R.string.error_message
                            )
                        )
                    )
                }
            }

        }
    }

    fun loadSettings() {
        viewModelScope.launch {
            val searchType = getSearchTypeUseCase.runUseCase()
            val listType = getListTypeUseCase.runUseCase()
            val settingsType = getSettingsTypeUseCase.runUseCase()
            sendEvent(CatsScreenReducer.CatsScreenEvent.LoadSettings(searchType, settingsType, listType))
        }
    }

    fun addFavoriteCat(cat: Cat) {
        viewModelScope.launch {
            if (addFavoriteCatUseCase.runUseCase(cat)) {
                Log.d("room-test", "added favorite cat " + cat.name)
                sendEvent(CatsScreenReducer.CatsScreenEvent.UpdateCatIsFollowed(cat))
            }
        }
    }

    fun deleteFavoriteCat(cat: Cat) {
        viewModelScope.launch {
            if (deleteFavoriteCatUseCase.runUseCase(cat)) {
                Log.d("room-test", "deleted favorite cat " + cat.name)
                sendEvent(CatsScreenReducer.CatsScreenEvent.UpdateCatIsFollowed(cat.copy(isFavorite = false)))
            }
        }
    }


    fun localSearch(query: String) {
        var result: List<Cat> = listOf()
        if (query.isEmpty()) {
            result = state.value.catsBackup!!
        } else {
            result = state.value.catsBackup!!.filter {
                it.name?.lowercase()?.contains(query.lowercase()) ?: false
                        || it.origin?.lowercase()?.contains(query.lowercase()) ?: false
            }
        }

        sendEvent(CatsScreenReducer.CatsScreenEvent.DataChanged(result))
    }

    fun remoteSearch(query: String) {
        if (query.isEmpty()) {
            sendEvent(CatsScreenReducer.CatsScreenEvent.DataChanged(state.value.catsBackup!!))
        } else {
            sendEvent(CatsScreenReducer.CatsScreenEvent.Loading)
            viewModelScope.launch {
                //delay for test
                delay(3000)
                when (val response = searchForCatsUseCase.runUseCase(query)) {
                    is ApiResponse.Success<List<Cat>> -> {
                        sendEvent(
                            CatsScreenReducer.CatsScreenEvent.DataReceivedWithSearch(
                                response.data,
                                null
                            )
                        )
                    }

                    is ApiResponse.Error -> {
                        sendEvent(
                            CatsScreenReducer.CatsScreenEvent.DataReceivedWithSearch(
                                null, context.getString(
                                    R.string.error_message
                                )
                            )
                        )
                    }
                }

            }
        }

    }

    fun navigateToCatDetail(cat: Cat) {
        sendEventForEffect(CatsScreenReducer.CatsScreenEvent.CatDetail(cat))
    }

    fun navigateToSettings() {
        sendEvent(CatsScreenReducer.CatsScreenEvent.OpenSettings)
    }

    fun closeSettings() {
        sendEvent(CatsScreenReducer.CatsScreenEvent.CloseSettings)
    }

    fun settingsUpdated(
        viewTypeForList: ViewTypeForList,
        viewTypeForSettings: ViewTypeForSettings,
        searchType: SearchType
    ) {
        viewModelScope.launch {
            saveListTypeUseCase.runUseCase(viewTypeForList.name)
            saveSettingsTypeUseCase.runUseCase(viewTypeForSettings.name)
            saveSearchTypeUseCase.runUseCase(searchType.name)
            sendEvent(CatsScreenReducer.CatsScreenEvent.SettingsUpdated(viewTypeForList, viewTypeForSettings, searchType))
        }
    }

    fun favoriteAnimalsChanged(favoriteAnimalsFlow: Flow<List<Cat>>) {
        viewModelScope.launch {
            favoriteAnimalsFlow.stateIn(this).collectLatest { favoriteList ->
                Log.d("room-test", "favorite list count: " + favoriteList.size.toString())
                sendEvent(CatsScreenReducer.CatsScreenEvent.FavoriteAnimalCountChanged(favoriteList))
            }
        }
    }

    fun showBigImage(cat: Cat) {
        sendEvent(CatsScreenReducer.CatsScreenEvent.ShowBigImage(cat))
    }

    fun closeBigImage() {
        sendEvent(CatsScreenReducer.CatsScreenEvent.CloseBigImage)
    }
}