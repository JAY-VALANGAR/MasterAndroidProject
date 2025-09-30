package com.jayvalangar.masterandroidproject.util


// This is a special class that tells us the state of data from the internet.
// It can be loading, successful, or an error.
//----------------------------------------------------------------------------------------------------
sealed class ApiThreeState<out T> {
    // Shows that data is still loading (spinning circle).
    class Loading<out T> : ApiThreeState<T>()

    // Shows that data loaded successfully with the actual data.
    data class Success<out T>(val data: T) : ApiThreeState<T>()

    // Shows that there was an error with a message and maybe an error code.
    data class Error<out T>(val message: String, val code: Int? = null) : ApiThreeState<T>()
}

//----------------------------------------------------------------------------------------------------