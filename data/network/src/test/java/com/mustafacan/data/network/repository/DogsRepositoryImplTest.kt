package com.mustafacan.data.network.repository

import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import kotlin.test.assertEquals
import kotlin.test.assertIs
import com.mustafacan.data.network.datasource.DogsRemoteDataSource
import com.mustafacan.domain.model.dogs.Dog
import com.mustafacan.domain.model.error.CustomException
import com.mustafacan.domain.model.response.ApiResponse
import com.mustafacan.domain.usecase.dogs.roomdb.GetFavoriteDogsUseCase

class DogsRepositoryImplTest {
    private val remoteDataSource: DogsRemoteDataSource = mock()
    private val getFavoriteDogsUseCase: GetFavoriteDogsUseCase = mock()
    private val dogsRepositoryImp = DogsRepositoryImpl(remoteDataSource, getFavoriteDogsUseCase)

    /************started getDogs() tests************/
    @Test
    fun `image_should_change_when_after_getDogs_response`() = runTest{
        `when`(remoteDataSource.getDogs()).thenReturn(ApiResponse.Success(listOf(Dog(id = 1, name = "Golden Retriever", isFavorite = false, image = "https://fakeimg.pl/500x500/cc6633"))))
        `when`(getFavoriteDogsUseCase.runUseCase()).thenReturn(listOf(Dog(id = 1, name = "Golden Retriever", isFavorite = true)))
        val response = dogsRepositoryImp.getDogs()
        assertEquals("https://cdn.pixabay.com/photo/2019/06/22/19/01/golden-retriever-4292254_1280.jpg", (response as? ApiResponse.Success<List<Dog>>)?.data?.get(0)?.image)
    }

    @Test
    fun `isFavorite_should_true_when_after_getDogs_response`() = runTest{
        `when`(remoteDataSource.getDogs()).thenReturn(ApiResponse.Success(listOf(Dog(id = 1, name = "Golden Retriever", isFavorite = false))))
        `when`(getFavoriteDogsUseCase.runUseCase()).thenReturn(listOf(Dog(id = 1, name = "Golden Retriever", isFavorite = true)))
        val response = dogsRepositoryImp.getDogs()
        assertEquals(true, (response as? ApiResponse.Success<List<Dog>>)?.data?.get(0)?.isFavorite)
    }

    @Test
    fun `isFavorite_should_false_when_after_getDogs_response`() = runTest{
        `when`(remoteDataSource.getDogs()).thenReturn(ApiResponse.Success(listOf(Dog(id = 4, name = "Beagle", isFavorite = false))))
        `when`(getFavoriteDogsUseCase.runUseCase()).thenReturn(listOf(Dog(id = 1, name = "Golden Retriever", isFavorite = true)))
        val response = dogsRepositoryImp.getDogs()
        assertEquals(false, (response as? ApiResponse.Success<List<Dog>>)?.data?.get(0)?.isFavorite)
    }

    @Test
    fun `exception_should_ConnectionError_when_connection_error_is_returned`() = runTest{
        `when`(remoteDataSource.getDogs()).thenReturn(ApiResponse.Error(exception = CustomException.ConnectionError(msg = "ConnectionError")))
        val response = dogsRepositoryImp.getDogs()
        assertIs<CustomException.ConnectionError>((response as? ApiResponse.Error<List<Dog>>)?.exception)
    }

    @Test
    fun `exception_should_SocketTimeoutException_when_SocketTimeoutException_error_is_returned`() = runTest{
        `when`(remoteDataSource.getDogs()).thenReturn(ApiResponse.Error(exception = CustomException.SocketTimeoutException(msg = "SocketTimeoutException")))
        val response = dogsRepositoryImp.getDogs()
        assertIs<CustomException.SocketTimeoutException>((response as? ApiResponse.Error<List<Dog>>)?.exception)
    }

    @Test
    fun `exception_should_SSLHandshakeException_when_SSLHandshakeException_error_is_returned`() = runTest{
        `when`(remoteDataSource.getDogs()).thenReturn(ApiResponse.Error(exception = CustomException.SSLHandshakeException(msg = "SSLHandshakeException")))
        val response = dogsRepositoryImp.getDogs()
        assertIs<CustomException.SSLHandshakeException>((response as? ApiResponse.Error<List<Dog>>)?.exception)
    }

    @Test
    fun `exception_should_DefaultError_when_an_error_is_returned`() = runTest{
        `when`(remoteDataSource.getDogs()).thenReturn(ApiResponse.Error(exception = CustomException.DefaultError))
        val response = dogsRepositoryImp.getDogs()
        assertIs<CustomException.DefaultError>((response as? ApiResponse.Error<List<Dog>>)?.exception)
    }

    /************Finished getDogs() tests************/


    /************Started search(query: String) tests************/

    @Test
    fun `image_should_change_when_after_search_response`() = runTest{
        `when`(remoteDataSource.search("Golden Retriever")).thenReturn(ApiResponse.Success(listOf(Dog(id = 1, name = "Golden Retriever", isFavorite = false, image = "https://fakeimg.pl/500x500/cc6633"))))
        `when`(getFavoriteDogsUseCase.runUseCase()).thenReturn(listOf(Dog(id = 1, name = "Golden Retriever", isFavorite = true)))
        val response = dogsRepositoryImp.search("Golden Retriever")
        assertEquals("https://cdn.pixabay.com/photo/2019/06/22/19/01/golden-retriever-4292254_1280.jpg", (response as? ApiResponse.Success<List<Dog>>)?.data?.get(0)?.image)
    }

    @Test
    fun `isFavorite_should_true_when_after_search_response`() = runTest{
        `when`(remoteDataSource.search("Golden Retriever")).thenReturn(ApiResponse.Success(listOf(Dog(id = 1, name = "Golden Retriever", isFavorite = false))))
        `when`(getFavoriteDogsUseCase.runUseCase()).thenReturn(listOf(Dog(id = 1, name = "Golden Retriever", isFavorite = true)))
        val response = dogsRepositoryImp.search("Golden Retriever")
        assertEquals(true, (response as? ApiResponse.Success<List<Dog>>)?.data?.get(0)?.isFavorite)
    }

    @Test
    fun `isFavorite_should_false_when_after_search_response`() = runTest{
        `when`(remoteDataSource.search("Beagle")).thenReturn(ApiResponse.Success(listOf(Dog(id = 4, name = "Beagle", isFavorite = false))))
        `when`(getFavoriteDogsUseCase.runUseCase()).thenReturn(listOf(Dog(id = 1, name = "Golden Retriever", isFavorite = true)))
        val response = dogsRepositoryImp.search("Beagle")
        assertEquals(false, (response as? ApiResponse.Success<List<Dog>>)?.data?.get(0)?.isFavorite)
    }

    @Test
    fun `exception_should_ConnectionError_when_connection_error_is_returned_from_search_request`() = runTest{
        `when`(remoteDataSource.search("Golden Retriever")).thenReturn(ApiResponse.Error(exception = CustomException.ConnectionError(msg = "ConnectionError")))
        val response = dogsRepositoryImp.search("Golden Retriever")
        assertIs<CustomException.ConnectionError>((response as? ApiResponse.Error<List<Dog>>)?.exception)
    }

    @Test
    fun `exception_should_SocketTimeoutException_when_SocketTimeoutException_error_is_returned_from_search_request`() = runTest{
        `when`(remoteDataSource.search("Golden Retriever")).thenReturn(ApiResponse.Error(exception = CustomException.SocketTimeoutException(msg = "SocketTimeoutException")))
        val response = dogsRepositoryImp.search("Golden Retriever")
        assertIs<CustomException.SocketTimeoutException>((response as? ApiResponse.Error<List<Dog>>)?.exception)
    }

    @Test
    fun `exception_should_SSLHandshakeException_when_SSLHandshakeException_error_is_returned_from_search_request`() = runTest{
        `when`(remoteDataSource.search("Golden Retriever")).thenReturn(ApiResponse.Error(exception = CustomException.SSLHandshakeException(msg = "SSLHandshakeException")))
        val response = dogsRepositoryImp.search("Golden Retriever")
        assertIs<CustomException.SSLHandshakeException>((response as? ApiResponse.Error<List<Dog>>)?.exception)
    }

    @Test
    fun `exception_should_DefaultError_when_an_error_is_returned_from_search_request`() = runTest{
        `when`(remoteDataSource.search("Golden Retriever")).thenReturn(ApiResponse.Error(exception = CustomException.DefaultError))
        val response = dogsRepositoryImp.search("Golden Retriever")
        assertIs<CustomException.DefaultError>((response as? ApiResponse.Error<List<Dog>>)?.exception)
    }

    /************Finished search(query: String) tests************/

}