package com.mustafacan.data.remote.repository

import com.mustafacan.data.local.datasource.roomdatabase.FavoriteAnimalsDao
import com.mustafacan.data.local.repository.TempRepositoryImpl
import com.mustafacan.data.remote.datasource.DogsRemoteDataSource
import com.mustafacan.domain.model.dogs.Dog
import com.mustafacan.domain.model.error.CustomException
import com.mustafacan.domain.model.response.ApiResponse
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import kotlin.test.assertEquals
import kotlin.test.assertIs

class DogsRepositoryImplTest {
    val remoteDataSource: DogsRemoteDataSource = mock()
    val dao: FavoriteAnimalsDao = mock()

    /************started getDogs() tests************/
    @Test
    fun `image_should_change_when_after_getDogs_response`() = runTest{
        `when`(remoteDataSource.getDogs()).thenReturn(ApiResponse.Success(listOf(Dog(id = 1, name = "Golden Retriever", isFavorite = false, image = "https://fakeimg.pl/500x500/cc6633"))))
        `when`(dao.getDogs()).thenReturn(listOf(Dog(id = 1, name = "Golden Retriever", isFavorite = true)))
        val dogsRepositoryImp = DogsRepositoryImpl(remoteDataSource, dao)
        val response = dogsRepositoryImp.getDogs()
        assertIs<ApiResponse.Success<List<Dog>>>(response)
        assertEquals("https://cdn.pixabay.com/photo/2019/06/22/19/01/golden-retriever-4292254_1280.jpg", response.data.get(0).image)
    }

    @Test
    fun `isFavorite_should_true_when_after_getDogs_response`() = runTest{
        `when`(remoteDataSource.getDogs()).thenReturn(ApiResponse.Success(listOf(Dog(id = 1, name = "Golden Retriever", isFavorite = false))))
        `when`(dao.getDogs()).thenReturn(listOf(Dog(id = 1, name = "Golden Retriever", isFavorite = true)))
        val dogsRepositoryImp = DogsRepositoryImpl(remoteDataSource, dao)
        val response = dogsRepositoryImp.getDogs()
        assertIs<ApiResponse.Success<List<Dog>>>(response)
        assertEquals(true, response.data.get(0).isFavorite)
    }

    @Test
    fun `isFavorite_should_false_when_after_getDogs_response`() = runTest{
        `when`(remoteDataSource.getDogs()).thenReturn(ApiResponse.Success(listOf(Dog(id = 4, name = "Beagle", isFavorite = false))))
        `when`(dao.getDogs()).thenReturn(listOf(Dog(id = 1, name = "Golden Retriever", isFavorite = true)))
        val dogsRepositoryImp = DogsRepositoryImpl(remoteDataSource, dao)
        val response = dogsRepositoryImp.getDogs()
        assertIs<ApiResponse.Success<List<Dog>>>(response)
        assertEquals(false, response.data.get(0).isFavorite)
    }

    @Test
    fun `exception_should_ConnectionError_when_connection_error_is_returned`() = runTest{
        `when`(remoteDataSource.getDogs()).thenReturn(ApiResponse.Error(exception = CustomException.ConnectionError(msg = "ConnectionError")))

        val dogsRepositoryImp = DogsRepositoryImpl(remoteDataSource, dao)

        val response = dogsRepositoryImp.getDogs()

        assertIs<ApiResponse.Error<List<Dog>>>(response)
        assertIs<CustomException.ConnectionError>(response.exception)
    }

    @Test
    fun `exception_should_SocketTimeoutException_when_SocketTimeoutException_error_is_returned`() = runTest{
        `when`(remoteDataSource.getDogs()).thenReturn(ApiResponse.Error(exception = CustomException.SocketTimeoutException(msg = "SocketTimeoutException")))

        val dogsRepositoryImp = DogsRepositoryImpl(remoteDataSource, dao)

        val response = dogsRepositoryImp.getDogs()

        assertIs<ApiResponse.Error<List<Dog>>>(response)
        assertIs<CustomException.SocketTimeoutException>(response.exception)
    }

    @Test
    fun `exception_should_SSLHandshakeException_when_SSLHandshakeException_error_is_returned`() = runTest{
        `when`(remoteDataSource.getDogs()).thenReturn(ApiResponse.Error(exception = CustomException.SSLHandshakeException(msg = "SSLHandshakeException")))

        val dogsRepositoryImp = DogsRepositoryImpl(remoteDataSource, dao)

        val response = dogsRepositoryImp.getDogs()

        assertIs<ApiResponse.Error<List<Dog>>>(response)
        assertIs<CustomException.SSLHandshakeException>(response.exception)
    }

    @Test
    fun `exception_should_DefaultError_when_an_error_is_returned`() = runTest{
        `when`(remoteDataSource.getDogs()).thenReturn(ApiResponse.Error(exception = CustomException.DefaultError))

        val dogsRepositoryImp = DogsRepositoryImpl(remoteDataSource, dao)

        val response = dogsRepositoryImp.getDogs()

        assertIs<ApiResponse.Error<List<Dog>>>(response)
        assertIs<CustomException.DefaultError>(response.exception)
    }

    /************Finished getDogs() tests************/


    /************Started search(query: String) tests************/

    @Test
    fun `image_should_change_when_after_search_response`() = runTest{
        `when`(remoteDataSource.search("Golden Retriever")).thenReturn(ApiResponse.Success(listOf(Dog(id = 1, name = "Golden Retriever", isFavorite = false, image = "https://fakeimg.pl/500x500/cc6633"))))
        `when`(dao.getDogs()).thenReturn(listOf(Dog(id = 1, name = "Golden Retriever", isFavorite = true)))
        val dogsRepositoryImp = DogsRepositoryImpl(remoteDataSource, dao)
        val response = dogsRepositoryImp.search("Golden Retriever")
        assertIs<ApiResponse.Success<List<Dog>>>(response)
        assertEquals("https://cdn.pixabay.com/photo/2019/06/22/19/01/golden-retriever-4292254_1280.jpg", response.data.get(0).image)
    }

    @Test
    fun `isFavorite_should_true_when_after_search_response`() = runTest{
        `when`(remoteDataSource.search("Golden Retriever")).thenReturn(ApiResponse.Success(listOf(Dog(id = 1, name = "Golden Retriever", isFavorite = false))))
        `when`(dao.getDogs()).thenReturn(listOf(Dog(id = 1, name = "Golden Retriever", isFavorite = true)))
        val dogsRepositoryImp = DogsRepositoryImpl(remoteDataSource, dao)
        val response = dogsRepositoryImp.search("Golden Retriever")
        assertIs<ApiResponse.Success<List<Dog>>>(response)
        assertEquals(true, response.data.get(0).isFavorite)
    }

    @Test
    fun `isFavorite_should_false_when_after_search_response`() = runTest{
        `when`(remoteDataSource.search("Beagle")).thenReturn(ApiResponse.Success(listOf(Dog(id = 4, name = "Beagle", isFavorite = false))))
        `when`(dao.getDogs()).thenReturn(listOf(Dog(id = 1, name = "Golden Retriever", isFavorite = true)))
        val dogsRepositoryImp = DogsRepositoryImpl(remoteDataSource, dao)
        val response = dogsRepositoryImp.search("Beagle")
        assertIs<ApiResponse.Success<List<Dog>>>(response)
        assertEquals(false, response.data.get(0).isFavorite)
    }

    @Test
    fun `exception_should_ConnectionError_when_connection_error_is_returned_from_search_request`() = runTest{
        `when`(remoteDataSource.search("Golden Retriever")).thenReturn(ApiResponse.Error(exception = CustomException.ConnectionError(msg = "ConnectionError")))
        val dogsRepositoryImp = DogsRepositoryImpl(remoteDataSource, dao)
        val response = dogsRepositoryImp.search("Golden Retriever")
        assertIs<ApiResponse.Error<List<Dog>>>(response)
        assertIs<CustomException.ConnectionError>(response.exception)
    }

    @Test
    fun `exception_should_SocketTimeoutException_when_SocketTimeoutException_error_is_returned_from_search_request`() = runTest{
        `when`(remoteDataSource.search("Golden Retriever")).thenReturn(ApiResponse.Error(exception = CustomException.SocketTimeoutException(msg = "SocketTimeoutException")))
        val dogsRepositoryImp = DogsRepositoryImpl(remoteDataSource, dao)
        val response = dogsRepositoryImp.search("Golden Retriever")
        assertIs<ApiResponse.Error<List<Dog>>>(response)
        assertIs<CustomException.SocketTimeoutException>(response.exception)
    }

    @Test
    fun `exception_should_SSLHandshakeException_when_SSLHandshakeException_error_is_returned_from_search_request`() = runTest{
        `when`(remoteDataSource.search("Golden Retriever")).thenReturn(ApiResponse.Error(exception = CustomException.SSLHandshakeException(msg = "SSLHandshakeException")))
        val dogsRepositoryImp = DogsRepositoryImpl(remoteDataSource, dao)
        val response = dogsRepositoryImp.search("Golden Retriever")
        assertIs<ApiResponse.Error<List<Dog>>>(response)
        assertIs<CustomException.SSLHandshakeException>(response.exception)
    }

    @Test
    fun `exception_should_DefaultError_when_an_error_is_returned_from_search_request`() = runTest{
        `when`(remoteDataSource.search("Golden Retriever")).thenReturn(ApiResponse.Error(exception = CustomException.DefaultError))
        val dogsRepositoryImp = DogsRepositoryImpl(remoteDataSource, dao)
        val response = dogsRepositoryImp.search("Golden Retriever")
        assertIs<ApiResponse.Error<List<Dog>>>(response)
        assertIs<CustomException.DefaultError>(response.exception)
    }

    /************Finished search(query: String) tests************/

    /************Started getDogsWithTemporaryData() tests************/

    @Test
    fun `image_should_change_when_after_response_from_temporary_data`() = runTest{
        `when`(dao.getDogs()).thenReturn(listOf(Dog(id = 1, name = "Golden Retriever", isFavorite = true)))
        val tempRepositoryImp = TempRepositoryImpl(dao)
        val response = tempRepositoryImp.getTempDogs()
        assertIs<ApiResponse.Success<List<Dog>>>(response)
        assertEquals("https://cdn.pixabay.com/photo/2019/06/22/19/01/golden-retriever-4292254_1280.jpg", response.data.get(0).image)
    }

    @Test
    fun `isFavorite_should_true_when_after_response_from_temporary_data`() = runTest{
        `when`(dao.getDogs()).thenReturn(listOf(Dog(id = 1, name = "Golden Retriever", isFavorite = true)))
        val tempRepositoryImp = TempRepositoryImpl(dao)
        val response = tempRepositoryImp.getTempDogs()
        assertIs<ApiResponse.Success<List<Dog>>>(response)
        assertEquals(true, response.data.get(0).isFavorite)
    }

    @Test
    fun `isFavorite_should_false_when_after_response_from_temporary_data`() = runTest{
        `when`(dao.getDogs()).thenReturn(listOf(Dog(id = 2000, name = "Test name", isFavorite = true)))
        val tempRepositoryImp = TempRepositoryImpl(dao)
        val response = tempRepositoryImp.getTempDogs()
        assertIs<ApiResponse.Success<List<Dog>>>(response)
        assertEquals(false, response.data.get(0).isFavorite)
    }

    /************Finished getDogsWithTemporaryData() tests************/
}