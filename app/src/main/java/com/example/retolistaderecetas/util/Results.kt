package com.example.retolistaderecetas.util

sealed class Results<T> {
    data class Success<T>(val data: T? = null): Results<T>()
    data class Error<T>(val error: ErrorEntity): Results<T>()
}