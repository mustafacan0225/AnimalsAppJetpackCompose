package com.mustafacan.data.remote.api

import com.mustafacan.domain.model.error.CustomException
import com.mustafacan.domain.model.response.ApiResponse
import retrofit2.Response
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLHandshakeException

abstract class ResponseWrapper {


    protected suspend fun <T> getResult(call: suspend () -> Response<T>): ApiResponse<T & Any> {

        try {
            val response = call()
            when {
                response.isSuccessful -> {
                    response.body()?.let {
                        return ApiResponse.Success(data = it)
                    }
                }
                !response.isSuccessful -> {
                    throw getError(CustomException.DefaultError)
                }

            }

            throw getError()
        } catch (e: Exception) {
            return ApiResponse.Error(exception = getError(e))
        }

    }


    private fun getError(exception: Exception? = null): CustomException {
        return when (exception) {
            null -> {
                CustomException.DefaultError
            }
            is CustomException -> {
                exception
            }
            is SSLHandshakeException -> {
                CustomException.SSLHandshakeException(msg = exception.message?: "")
            }
            is SocketTimeoutException -> {
                CustomException.SocketTimeoutException(msg = exception.message?: "")
            }
            is UnknownHostException -> {
                CustomException.ConnectionError(msg = exception.message?: "")
            }
            else -> CustomException.DefaultError
        }
    }

}