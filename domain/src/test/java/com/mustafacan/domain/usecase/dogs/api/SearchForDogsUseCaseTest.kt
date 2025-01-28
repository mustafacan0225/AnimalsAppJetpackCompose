package com.mustafacan.domain.usecase.dogs.api

import com.mustafacan.domain.model.dogs.Dog
import com.mustafacan.domain.model.error.CustomException
import com.mustafacan.domain.model.response.ApiResponse
import com.mustafacan.domain.repository.api.DogsRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import kotlin.test.assertIs

class SearchForDogsUseCaseTest {

    private val dogsRepository: DogsRepository = mock()
    private val searchForDogsUseCase = SearchForDogsUseCase(dogsRepository)

    @Test
    fun `name_should_Golden_Retriver_when_after_search_response`() = runTest {
        `when`(dogsRepository.search("Golden")).thenReturn(ApiResponse.Success(listOf(Dog(id = 1, name = "Golden Retriever", isFavorite = false, image = "https://fakeimg.pl/500x500/cc6633"))))
        val response = searchForDogsUseCase.runUseCase("Golden")
        assertEquals("Golden Retriever", (response as? ApiResponse.Success<List<Dog>>)?.data?.get(0)?.name)
    }

    @Test
    fun `should_empty_list_when_empty_result_is_returned`() = runTest {
        `when`(dogsRepository.search("Golden")).thenReturn(ApiResponse.Success(listOf()))
        val response = searchForDogsUseCase.runUseCase("Golden")
        assertTrue((response as? ApiResponse.Success<List<Dog>>)?.data?.isEmpty() ?: false)
    }

    @Test
    fun `exception_should_ConnectionError_when_connection_error_is_returned`() = runTest {
        `when`(dogsRepository.search("Golden")).thenReturn(ApiResponse.Error(exception = CustomException.ConnectionError(msg = "ConnectionError")))
        val response = searchForDogsUseCase.runUseCase("Golden")
        assertIs<CustomException.ConnectionError>((response as? ApiResponse.Error<List<Dog>>)?.exception)
    }
}