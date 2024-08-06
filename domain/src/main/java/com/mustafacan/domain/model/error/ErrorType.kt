package com.mustafacan.domain.model.error

import java.io.IOException

sealed class ErrorType : IOException() {
    object ConnectionError : ErrorType()
    object SocketTimeoutException : ErrorType()
    object SSLHandshakeException : ErrorType()
    object JsonEncodingException : ErrorType()
    object DefaultError : ErrorType()
}
