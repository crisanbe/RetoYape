package com.example.retolistaderecetas.util

sealed class ErrorEntity {
    sealed class ApiError: ErrorEntity() {
        object NotFound : ApiError()
        object UnKnown: ApiError()
    }
    sealed class InputError: ErrorEntity() {
        object NameError: InputError()
    }
}