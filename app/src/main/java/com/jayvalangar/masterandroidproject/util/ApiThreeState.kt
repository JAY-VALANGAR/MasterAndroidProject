package com.jayvalangar.masterandroidproject.util

sealed class ApiThreeState<out T> {
    class Loading<out T> : ApiThreeState<T>()
    data class Success<out T>(val data: T) : ApiThreeState<T>()
    data class Error<out T>(val message: String, val code: Int? = null) : ApiThreeState<T>()
}