package com.mustafacan.cats.feature.cats

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.mustafacan.domain.model.cats.Cat
import com.mustafacan.domain.model.response.ApiResponse
import com.mustafacan.domain.usecase.allanimals.GetAllFavoriteAnimalsUseCase
import com.mustafacan.domain.usecase.cats.api_usecase.GetCatsUseCase
import com.mustafacan.domain.usecase.cats.api_usecase.SearchForCatsUseCase
import com.mustafacan.domain.usecase.cats.roomdb_usecase.AddFavoriteCatUseCase
import com.mustafacan.domain.usecase.cats.roomdb_usecase.DeleteFavoriteCatUseCase
import com.mustafacan.domain.usecase.cats.roomdb_usecase.GetFlowFavoriteCatsUseCase
import com.mustafacan.domain.usecase.cats.sharedpref_usecase.GetListTypeUseCase
import com.mustafacan.domain.usecase.cats.sharedpref_usecase.GetSearchTypeUseCase
import com.mustafacan.domain.usecase.cats.sharedpref_usecase.GetSettingsTypeUseCase
import com.mustafacan.domain.usecase.cats.sharedpref_usecase.SaveListTypeUseCase
import com.mustafacan.domain.usecase.cats.sharedpref_usecase.SaveSearchTypeUseCase
import com.mustafacan.domain.usecase.cats.sharedpref_usecase.SaveSettingsTypeUseCase
import com.mustafacan.domain.usecase.cats.temp.GetCatsWithTemporaryDataUseCase
import com.mustafacan.core.model.enums.SearchType
import com.mustafacan.core.model.enums.ViewTypeForList
import com.mustafacan.core.model.enums.ViewTypeForSettings
import com.mustafacan.core.viewmodel.BaseViewModel
import com.mustafacan.cats.R
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
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
    private val getFlowFavoriteCatsUseCase: GetFlowFavoriteCatsUseCase,
    private val deleteFavoriteCatUseCase: DeleteFavoriteCatUseCase,
    private val getSearchTypeUseCase: GetSearchTypeUseCase,
    private val getListTypeUseCase: GetListTypeUseCase,
    private val getSettingsTypeUseCase: GetSettingsTypeUseCase,
    private val saveListTypeUseCase: SaveListTypeUseCase,
    private val saveSearchTypeUseCase: SaveSearchTypeUseCase,
    private val saveSettingsTypeUseCase: SaveSettingsTypeUseCase,
    private val getCatsWithTemporaryDataUseCase: GetCatsWithTemporaryDataUseCase,
    private val getAllFavoriteAnimalsUseCase: GetAllFavoriteAnimalsUseCase,
) : BaseViewModel<CatsScreenUiStateManager.CatsScreenState, CatsScreenUiStateManager.CatsScreenEvent,
        CatsScreenUiStateManager.CatsScreenEffect>(
    initialState = CatsScreenUiStateManager.CatsScreenState.initial(),
    uiStateManager = CatsScreenUiStateManager()
) {

    init {

        callCats()
        listenFavoriteCats()
        listenAllFavoriteAnimals()

    }

    fun callCats() {
        sendEvent(CatsScreenUiStateManager.CatsScreenEvent.Loading)
        getCats()
    }

    fun getCats() {
        viewModelScope.launch {

            delay(3000)
            when (val response = getCatsUseCase.runUseCase()) {

                is ApiResponse.Success<List<Cat>> -> {
                    sendEvent(
                        CatsScreenUiStateManager.CatsScreenEvent.DataReceived(
                            response.data,
                            null
                        )
                    )
                }

                is ApiResponse.Error -> {
                    sendEvent(
                        CatsScreenUiStateManager.CatsScreenEvent.DataReceived(
                            null, context.getString(
                                R.string.error_message
                            )
                        )
                    )
                }
            }

        }
    }

    fun getCatsWithTemporaryData() {
        sendEvent(CatsScreenUiStateManager.CatsScreenEvent.Loading)
        viewModelScope.launch {
            delay(3000)
            when (val response = getCatsWithTemporaryDataUseCase.runUseCase()) {

                is ApiResponse.Success<List<Cat>> -> {
                    sendEvent(
                        CatsScreenUiStateManager.CatsScreenEvent.DataReceived(
                            response.data,
                            null
                        )
                    )
                }

                is ApiResponse.Error -> {
                    sendEvent(
                        CatsScreenUiStateManager.CatsScreenEvent.DataReceived(
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
            sendEvent(
                CatsScreenUiStateManager.CatsScreenEvent.LoadSettings(
                    searchType,
                    settingsType,
                    listType
                )
            )
        }
    }

    fun addFavoriteCat(cat: Cat) {
        viewModelScope.launch {
            if (addFavoriteCatUseCase.runUseCase(cat)) {
                sendEvent(CatsScreenUiStateManager.CatsScreenEvent.UpdateCatIsFollowed(cat))
            }
        }
    }

    fun deleteFavoriteCat(cat: Cat) {
        viewModelScope.launch {
            if (deleteFavoriteCatUseCase.runUseCase(cat)) {
                sendEvent(
                    CatsScreenUiStateManager.CatsScreenEvent.UpdateCatIsFollowed(
                        cat.copy(
                            isFavorite = false
                        )
                    )
                )
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

        sendEvent(CatsScreenUiStateManager.CatsScreenEvent.DataChanged(result))
    }

    fun remoteSearch(query: String) {
        if (query.isEmpty()) {
            sendEvent(CatsScreenUiStateManager.CatsScreenEvent.DataChanged(state.value.catsBackup!!))
        } else {
            sendEvent(CatsScreenUiStateManager.CatsScreenEvent.Loading)
            viewModelScope.launch {
                //delay for test
                delay(3000)
                when (val response = searchForCatsUseCase.runUseCase(query)) {
                    is ApiResponse.Success<List<Cat>> -> {
                        sendEvent(
                            CatsScreenUiStateManager.CatsScreenEvent.DataReceivedWithSearch(
                                response.data,
                                null
                            )
                        )
                    }

                    is ApiResponse.Error -> {
                        sendEvent(
                            CatsScreenUiStateManager.CatsScreenEvent.DataReceivedWithSearch(
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
        sendEventForEffect(CatsScreenUiStateManager.CatsScreenEvent.CatDetail(cat))
    }

    fun navigateToSettings() {
        sendEvent(CatsScreenUiStateManager.CatsScreenEvent.OpenSettings)
    }

    fun closeSettings() {
        sendEvent(CatsScreenUiStateManager.CatsScreenEvent.CloseSettings)
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
            sendEvent(
                CatsScreenUiStateManager.CatsScreenEvent.SettingsUpdated(
                    viewTypeForList,
                    viewTypeForSettings,
                    searchType
                )
            )
        }
    }

    fun listenFavoriteCats() {
        viewModelScope.launch {
            val favoriteCatsFlow = getFlowFavoriteCatsUseCase.runUseCase()
            favoriteCatsFlow.stateIn(this).collectLatest { favoriteList ->
                sendEvent(CatsScreenUiStateManager.CatsScreenEvent.FavoriteCatsChanged(favoriteList))
            }
        }
    }

    fun listenAllFavoriteAnimals() {
        viewModelScope.launch {
            val allFavoriteAnimalsFlow = getAllFavoriteAnimalsUseCase.runUseCase()
            allFavoriteAnimalsFlow.collectLatest {
                sendEvent(CatsScreenUiStateManager.CatsScreenEvent.AllFavoriteAnimalsChanged(it))
            }
        }
    }

    fun showBigImage(cat: Cat) {
        sendEvent(CatsScreenUiStateManager.CatsScreenEvent.ShowBigImage(cat))
    }

    fun closeBigImage() {
        sendEvent(CatsScreenUiStateManager.CatsScreenEvent.CloseBigImage)
    }

    fun showAllFavoriteAnimals() {
        sendEvent(CatsScreenUiStateManager.CatsScreenEvent.ShowAllFavoriteAnimals)
    }

    fun closeAllFavoriteAnimals() {
        sendEvent(CatsScreenUiStateManager.CatsScreenEvent.CloseAllFavoriteAnimals)
    }
}