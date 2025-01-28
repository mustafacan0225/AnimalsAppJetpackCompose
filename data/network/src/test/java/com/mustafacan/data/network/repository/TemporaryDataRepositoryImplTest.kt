package com.mustafacan.data.network.repository

import org.junit.Assert.*
import com.mustafacan.domain.model.dogs.Dog
import com.mustafacan.domain.model.response.ApiResponse
import com.mustafacan.domain.usecase.birds.roomdb.GetFavoriteBirdsUseCase
import com.mustafacan.domain.usecase.cats.roomdb.GetFavoriteCatsUseCase
import com.mustafacan.domain.usecase.dogs.roomdb.GetFavoriteDogsUseCase
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class TemporaryDataRepositoryImplTest {
    private val getFavoriteDogsUseCase: GetFavoriteDogsUseCase = mock()
    private val getFavoriteCatsUseCase: GetFavoriteCatsUseCase = mock()
    private val getFavoriteBirdsUseCase: GetFavoriteBirdsUseCase = mock()
    private val tempRepositoryImpl = TemporaryDataRepositoryImpl(getFavoriteDogsUseCase, getFavoriteCatsUseCase, getFavoriteBirdsUseCase)

    @Test
    fun `image_should_change_when_after_response_from_temporary_data`() = runTest{
        `when`(getFavoriteDogsUseCase.runUseCase()).thenReturn(listOf(Dog(id = 1, name = "Golden Retriever", isFavorite = true)))
        val response = tempRepositoryImpl.getTempDogs()
        assertEquals("https://cdn.pixabay.com/photo/2019/06/22/19/01/golden-retriever-4292254_1280.jpg", (response as? ApiResponse.Success<List<Dog>>)?.data?.get(0)?.image)
    }

    @Test
    fun `isFavorite_should_true_when_after_response_from_temporary_data`() = runTest{
        `when`(getFavoriteDogsUseCase.runUseCase()).thenReturn(listOf(Dog(id = 1, name = "Golden Retriever", isFavorite = true)))
        val response = tempRepositoryImpl.getTempDogs()
        assertEquals(true, (response as? ApiResponse.Success<List<Dog>>)?.data?.get(0)?.isFavorite)
    }

    @Test
    fun `isFavorite_should_false_when_after_response_from_temporary_data`() = runTest{
        `when`(getFavoriteDogsUseCase.runUseCase()).thenReturn(listOf(Dog(id = 2000, name = "Test name", isFavorite = true)))
        val response = tempRepositoryImpl.getTempDogs()
        assertEquals(false, (response as? ApiResponse.Success<List<Dog>>)?.data?.get(0)?.isFavorite)
    }

}