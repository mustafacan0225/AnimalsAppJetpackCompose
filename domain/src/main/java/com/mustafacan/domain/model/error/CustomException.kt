package com.mustafacan.domain.model.error

import java.io.IOException

sealed class CustomException(val errorMessage: String? = "An error occurred") : IOException() {
    data class ConnectionError(val msg: String) : CustomException(errorMessage = msg)
    data class SocketTimeoutException(val msg: String) : CustomException(errorMessage = msg)
    data class SSLHandshakeException(val msg: String) : CustomException(errorMessage = msg)
    data class JsonEncodingException(val msg: String) : CustomException(errorMessage = msg)
    object DefaultError : CustomException()
}
