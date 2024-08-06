package com.mustafacan.domain.usecase.base

import com.mustafacan.domain.model.error.ErrorType

abstract class BaseUseCase<Params, T> {

    protected abstract suspend fun runUseCase(params: Params): T

    suspend fun invoke(params: Params, onSucces: (T) -> Unit, onError: (ErrorType?) -> Unit) {
        try {
            val result = runUseCase(params)
            onSucces(result)
        } catch (e: ErrorType) {
            onError(e)
        }
    }

    object None {
        override fun toString() = "UseCase.None"
    }
}