package com.mustafacan.data.remote.datasource

import com.mustafacan.data.remote.api.DogsServices
import okhttp3.ResponseBody.Companion.toResponseBody
import com.mustafacan.domain.model.dogs.Dog
import com.mustafacan.domain.model.response.ApiResponse
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import retrofit2.Response
import kotlin.test.assertIs
import kotlin.test.assertTrue

class DogsRemoteDataSourceTest {
    private val api: DogsServices = mock()
    @Test
    fun `should_return_ApiResponse_Success_when_successful_result_is_returned`() = runTest{
        `when`(api.getDogs()).thenReturn(Response.success(listOf(Dog(id = 1, name = "Golden Retriever"))))

        val dataSource = DogsRemoteDataSource(api)
        val response = dataSource.getDogs()

        assertIs<ApiResponse.Success<List<Dog>>>(response)
        assertEquals(1, response.data.get(0).id)
    }

    @Test
    fun `should_return_ApiResponse_Success_empty_list_when_empty_result_is_returned`() = runTest{
        `when`(api.getDogs()).thenReturn(Response.success(listOf()))

        val dataSource = DogsRemoteDataSource(api)

        val response = dataSource.getDogs()

        assertIs<ApiResponse.Success<List<Dog>>>(response)
        assertTrue(response.data.isEmpty())
    }

    @Test
    fun `should_return_ApiResponse_Error_when_an_error_is_returned`() = runTest{
        `when`(api.getDogs()).thenReturn(Response.error(404, "An error occurred".toResponseBody(null)))

        val dataSource = DogsRemoteDataSource(api)

        val response = dataSource.getDogs()

        assertIs<ApiResponse.Error<List<Dog>>>(response)
    }

    @Test
    fun `name_should_Golden_Retriever_when_successful_result_is_returned`() = runTest {
        `when`(api.search("Golden Retriever")).thenReturn(Response.success(listOf(Dog(id = 1, name = "Golden Retriever"))))

        val dataSource = DogsRemoteDataSource(api)

        val response = dataSource.search("Golden Retriever")

        assertIs<ApiResponse.Success<List<Dog>>>(response)
        assertEquals("Golden Retriever", response.data.get(0).name)
    }

    @Test
    fun `should_return_empty_list_when_no_results_are_found`() = runTest {
        `when`(api.search("Poodle")).thenReturn(Response.success(emptyList()))

        val dataSource = DogsRemoteDataSource(api)

        val response = dataSource.search("Poodle")

        assertIs<ApiResponse.Success<List<Dog>>>(response)
        assertTrue(response.data.isEmpty())
    }

    @Test
    fun `should_return_ApiResponse_Error_when_an_error_is_returned_from_search_request`() = runTest{
        `when`(api.search("Poodle")).thenReturn(Response.error(404, "An error occurred".toResponseBody(null)))

        val dataSource = DogsRemoteDataSource(api)

        val response = dataSource.search("Poodle")

        assertIs<ApiResponse.Error<List<Dog>>>(response)
    }
}