package com.mustafacan.ui_dogs.feature.dogs

import android.content.Context
import com.mustafacan.domain.model.dogs.Dog
import com.mustafacan.domain.model.error.CustomException
import com.mustafacan.domain.model.response.ApiResponse
import com.mustafacan.domain.usecase.dogs.api_usecase.GetDogsUseCase
import com.mustafacan.domain.usecase.dogs.api_usecase.SearchForDogsUseCase
import com.mustafacan.domain.usecase.dogs.roomdb_usecase.AddFavoriteDogUseCase
import com.mustafacan.domain.usecase.dogs.roomdb_usecase.DeleteFavoriteDogUseCase
import com.mustafacan.domain.usecase.dogs.roomdb_usecase.GetFavoriteDogsUseCase
import com.mustafacan.domain.usecase.dogs.sharedpref_usecase.GetListTypeUseCase
import com.mustafacan.domain.usecase.dogs.sharedpref_usecase.GetSearchTypeUseCase
import com.mustafacan.domain.usecase.dogs.sharedpref_usecase.GetSettingsTypeUseCase
import com.mustafacan.domain.usecase.dogs.sharedpref_usecase.SaveListTypeUseCase
import com.mustafacan.domain.usecase.dogs.sharedpref_usecase.SaveSearchTypeUseCase
import com.mustafacan.domain.usecase.dogs.sharedpref_usecase.SaveSettingsTypeUseCase
import com.mustafacan.domain.usecase.dogs.temp.GetDogsWithTemporaryDataUseCase
import com.mustafacan.ui_common.model.enums.SearchType
import com.mustafacan.ui_common.model.enums.ViewTypeForList
import com.mustafacan.ui_common.model.enums.ViewTypeForSettings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import com.mustafacan.ui_dogs.R

class DogsViewModelTest {
    private val context: Context = mock()
    private val getDogsUseCase: GetDogsUseCase = mock()
    private val searchForDogsUseCase: SearchForDogsUseCase = mock()
    private val addFavoriteDogUseCase: AddFavoriteDogUseCase = mock()
    private val getFavoriteDogsUseCase: GetFavoriteDogsUseCase = mock()
    private val deleteFavoriteDogUseCase: DeleteFavoriteDogUseCase = mock()
    private val getSearchTypeUseCase: GetSearchTypeUseCase = mock()
    private val getListTypeUseCase: GetListTypeUseCase = mock()
    private val getSettingsTypeUseCase: GetSettingsTypeUseCase = mock()
    private val saveListTypeUseCase: SaveListTypeUseCase = mock()
    private val saveSearchTypeUseCase: SaveSearchTypeUseCase = mock()
    private val saveSettingsTypeUseCase: SaveSettingsTypeUseCase = mock()
    private val getDogsWithTemporaryDataUseCase: GetDogsWithTemporaryDataUseCase = mock()
    private lateinit var viewModel: DogsViewModel

    @get:Rule(order = 1)
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setUp() = runTest{
        `when`(getFavoriteDogsUseCase.runUseCase()).thenReturn(flowOf(listOf()))
        viewModel = DogsViewModel(context, getDogsUseCase, searchForDogsUseCase, addFavoriteDogUseCase, getFavoriteDogsUseCase, deleteFavoriteDogUseCase, getSearchTypeUseCase, getListTypeUseCase, getSettingsTypeUseCase, saveListTypeUseCase, saveSearchTypeUseCase, saveSettingsTypeUseCase, getDogsWithTemporaryDataUseCase)

    }

    @Test
    fun `dogs_should_not_null_when_after_getDogs_succesfuly_response`() = runTest {
        assertNull(viewModel.state.value.dogs)
        `when`(getDogsUseCase.runUseCase()).thenReturn(ApiResponse.Success(listOf(Dog(id = 1, name = "Golden Retriever", isFavorite = false))))
        viewModel.getDogs()
        delay(3000)
        assertNotNull(viewModel.state.value.dogs)
    }

    @Test
    fun `dogs_should_is_empty_list_when_after_getDogs_empty_list_response`() = runTest {
        assertNull(viewModel.state.value.dogs)
        `when`(getDogsUseCase.runUseCase()).thenReturn(ApiResponse.Success(listOf()))
        viewModel.getDogs()
        delay(3000)
        assertTrue(viewModel.state.value.dogs?.isEmpty()?: false)
    }

    @Test
    fun `errorMessage_should_not_null_when_after_getDogs_error_response`() = runTest {
        `when`(context.getString(R.string.error_message)).thenReturn("An error occurred")
        `when`(getDogsUseCase.runUseCase()).thenReturn(ApiResponse.Error(exception = CustomException.ConnectionError(msg = "test")))
        viewModel.getDogs()
        delay(3000)
        assertNull(viewModel.state.value.dogs)
        assertNotNull(viewModel.state.value.errorMessage)
    }

    @Test
    fun `dogs_should_not_null_when_after_getDogsWithTemporaryData_succesfuly_response`() = runTest {
        assertNull(viewModel.state.value.dogs)
        `when`(getDogsWithTemporaryDataUseCase.runUseCase()).thenReturn(ApiResponse.Success(listOf(Dog(id = 1, name = "Golden Retriever", isFavorite = false))))
        viewModel.getDogsWithTemporaryData()
        delay(3000)
        assertNotNull(viewModel.state.value.dogs)
    }

    @Test
    fun `dogs_should_is_empty_list_when_after_getDogsWithTemporaryData_empty_list_response`() = runTest {
        assertNull(viewModel.state.value.dogs)
        `when`(getDogsWithTemporaryDataUseCase.runUseCase()).thenReturn(ApiResponse.Success(listOf()))
        viewModel.getDogsWithTemporaryData()
        delay(3000)
        assertTrue(viewModel.state.value.dogs?.isEmpty()?: false)
    }

    @Test
    fun `errorMessage_should_not_null_when_after_getDogsWithTemporaryData_error_response`() = runTest {
        `when`(context.getString(R.string.error_message)).thenReturn("An error occurred")
        `when`(getDogsWithTemporaryDataUseCase.runUseCase()).thenReturn(ApiResponse.Error(exception = CustomException.ConnectionError(msg = "test")))
        viewModel.getDogsWithTemporaryData()
        delay(3000)
        assertNull(viewModel.state.value.dogs)
        assertNotNull(viewModel.state.value.errorMessage)
    }

    @Test
    fun `load_settings`() = runTest {
        `when`(getSearchTypeUseCase.runUseCase()).thenReturn(SearchType.REMOTE_SEARCH.name)
        `when`(getListTypeUseCase.runUseCase()).thenReturn(ViewTypeForList.LAZY_VERTICAL_GRID.name)
        `when`(getSettingsTypeUseCase.runUseCase()).thenReturn(ViewTypeForSettings.BOTTOM_SHEET.name)
        viewModel.loadSettings()
        assertEquals(SearchType.REMOTE_SEARCH.name, viewModel.state.value.searchType.name)
        assertEquals(ViewTypeForList.LAZY_VERTICAL_GRID.name, viewModel.state.value.viewTypeForList.name)
        assertEquals(ViewTypeForSettings.BOTTOM_SHEET.name, viewModel.state.value.viewTypeForSettings.name)
    }

    @Test
    fun `add_favorite_dog`() = runTest {
        viewModel.sendEvent(DogsScreenUiStateManager.DogsScreenEvent.DataReceived(listOf(Dog(id = 1, "Golden", isFavorite = false))))
        `when`(addFavoriteDogUseCase.runUseCase(Dog(id = 1, "Golden", isFavorite = true))).thenReturn(true)
        viewModel.addFavoriteDog(Dog(id = 1, "Golden", isFavorite = true))
        assertTrue(viewModel.state.value.dogs?.get(0)?.isFavorite?: false)
        assertTrue(viewModel.state.value.dogsBackup?.get(0)?.isFavorite?: false)
    }

    @Test
    fun `delete_favorite_dog`() = runTest {
        viewModel.sendEvent(DogsScreenUiStateManager.DogsScreenEvent.DataReceived(listOf(Dog(id = 1, "Golden", isFavorite = true))))
        `when`(deleteFavoriteDogUseCase.runUseCase(Dog(id = 1, "Golden", isFavorite = false))).thenReturn(true)
        viewModel.deleteFavoriteDog(Dog(id = 1, "Golden", isFavorite = false))
        assertFalse(viewModel.state.value.dogs?.get(0)?.isFavorite?: true)
        assertFalse(viewModel.state.value.dogsBackup?.get(0)?.isFavorite?: true)
    }

    @Test
    fun `local_search_success_response`() = runTest {
        viewModel.sendEvent(DogsScreenUiStateManager.DogsScreenEvent.DataReceived(listOf(Dog(id = 1, "Golden", isFavorite = true), Dog(id = 2, "Bulldog", isFavorite = true))))
        viewModel.localSearch("golden")
        assertEquals("Golden", viewModel.state.value.dogs?.get(0)?.name)
        assertTrue(viewModel.state.value.dogsBackup?.size == 2)
    }

    @Test
    fun `local_search_empty_response`() = runTest {
        viewModel.sendEvent(DogsScreenUiStateManager.DogsScreenEvent.DataReceived(listOf(Dog(id = 1, "Golden", isFavorite = true), Dog(id = 2, "Bulldog", isFavorite = true))))
        viewModel.localSearch("test")
        assertTrue(viewModel.state.value.dogs?.isEmpty()?: false)
        assertTrue(viewModel.state.value.dogsBackup?.size == 2)
    }

    @Test
    fun `local_search_with_empty_query`() = runTest {
        viewModel.sendEvent(DogsScreenUiStateManager.DogsScreenEvent.DataReceived(listOf(Dog(id = 1, "Golden", isFavorite = true), Dog(id = 2, "Bulldog", isFavorite = true))))
        viewModel.localSearch("")
        assertEquals(viewModel.state.value.dogs, viewModel.state.value.dogsBackup)
    }

    @Test
    fun `remote_search_success_response`() = runTest {
        viewModel.sendEvent(DogsScreenUiStateManager.DogsScreenEvent.DataReceived(listOf(Dog(id = 1, "Golden", isFavorite = true), Dog(id = 2, "Bulldog", isFavorite = true))))
        `when`(searchForDogsUseCase.runUseCase("golden")).thenReturn(ApiResponse.Success(listOf(Dog(id = 1, "Golden", isFavorite = false))))
        viewModel.remoteSearch("golden")
        assertEquals("Golden", viewModel.state.value.dogs?.get(0)?.name)
        assertTrue(viewModel.state.value.dogsBackup?.size == 2)
    }

    @Test
    fun `remote_search_empty_response`() = runTest {
        viewModel.sendEvent(DogsScreenUiStateManager.DogsScreenEvent.DataReceived(listOf(Dog(id = 1, "Golden", isFavorite = true), Dog(id = 2, "Bulldog", isFavorite = true))))
        `when`(searchForDogsUseCase.runUseCase("test")).thenReturn(ApiResponse.Success(listOf()))
        viewModel.remoteSearch("test")
        delay(3000)
        assertTrue(viewModel.state.value.dogs?.isEmpty()?: false)
        assertTrue(viewModel.state.value.dogsBackup?.size == 2)
    }

    @Test
    fun `remote_search_error_response`() = runTest {
        viewModel.sendEvent(DogsScreenUiStateManager.DogsScreenEvent.DataReceived(listOf(Dog(id = 1, "Golden", isFavorite = true), Dog(id = 2, "Bulldog", isFavorite = true))))
        `when`(context.getString(R.string.error_message)).thenReturn("An error occurred")
        `when`(searchForDogsUseCase.runUseCase("test")).thenReturn(ApiResponse.Error(CustomException.ConnectionError("An error occured")))
        viewModel.remoteSearch("test")
        delay(3000)
        assertNull(viewModel.state.value.dogs)
        assertNotNull(viewModel.state.value.errorMessage)
    }

    @Test
    fun `remote_search_with_empty_query`() = runTest {
        viewModel.sendEvent(DogsScreenUiStateManager.DogsScreenEvent.DataReceived(listOf(Dog(id = 1, "Golden", isFavorite = true), Dog(id = 2, "Bulldog", isFavorite = true))))
        viewModel.remoteSearch("")
        assertEquals(viewModel.state.value.dogs, viewModel.state.value.dogsBackup)
    }

    @Test
    fun `open_settings`() = runTest {
        viewModel.navigateToSettings()
        assertTrue(viewModel.state.value.showSettings)
    }

    @Test
    fun `close_settings`() = runTest {
        viewModel.closeSettings()
        assertFalse(viewModel.state.value.showSettings)
    }

    @Test
    fun `show_big_image`() = runTest {
        viewModel.showBigImage(Dog(id = 1, "Golden", isFavorite = true))
        assertTrue(viewModel.state.value.showBigImage)
        assertEquals(Dog(id = 1, "Golden", isFavorite = true), viewModel.state.value.selectedDogForBigImage)
    }

    @Test
    fun `close_big_image`() = runTest {
        viewModel.closeBigImage()
        assertFalse(viewModel.state.value.showBigImage)
    }

    @Test
    fun `settings_updated`() {
        viewModel.settingsUpdated(ViewTypeForList.LAZY_COLUMN, ViewTypeForSettings.POPUP, SearchType.LOCAL_SEARCH)
        assertEquals(ViewTypeForList.LAZY_COLUMN, viewModel.state.value.viewTypeForList)
        assertEquals(ViewTypeForSettings.POPUP, viewModel.state.value.viewTypeForSettings)
        assertEquals(SearchType.LOCAL_SEARCH, viewModel.state.value.searchType)
    }
}

class MainDispatcherRule(val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()) :
    TestWatcher() {
    override fun starting(description: Description?) {
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description?) {
        Dispatchers.resetMain()
    }

}