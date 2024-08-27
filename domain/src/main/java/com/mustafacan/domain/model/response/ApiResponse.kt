package com.mustafacan.domain.model.response

import com.mustafacan.domain.model.error.CustomException

sealed class ApiResponse <T>(
    data: T? = null,
    exception: CustomException? = null
) {
    data class Success <T>(val data: T) : ApiResponse<T>(data, null)

    data class Error <T>(val exception: CustomException) : ApiResponse<T>(null, exception)

}