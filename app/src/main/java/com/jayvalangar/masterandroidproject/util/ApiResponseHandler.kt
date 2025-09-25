package com.jayvalangar.masterandroidproject.util

import retrofit2.Response

object ApiResponseHandler {

    fun <T> handleResponse(response: Response<T>): ApiThreeState<T> {
        return if (response.isSuccessful) {
            response.body()?.let { ApiThreeState.Success(it) }
                ?: ApiThreeState.Error("No data received", response.code())
        } else {
            val message = when (response.code()) {
                400 -> "Bad Request: Invalid input"
                401 -> "Unauthorized: Authentication failed"
                403 -> "Forbidden: Access denied"
                404 -> "Not Found: Resource not available"
                406 -> "Not Acceptable: Invalid format"
                413 -> "Payload Too Large: Request too big"
                429 -> "Too Many Requests: Rate limit exceeded"
                500 -> "Internal Server Error: Server issue"
                501 -> "Not Implemented: Feature not supported"
                502 -> "Bad Gateway: Invalid response from upstream"
                503 -> "Service Unavailable: Server overloaded"
                504 -> "Gateway Timeout: Server took too long"
                else -> "Unknown error (Code: ${response.code()})"
            }
            ApiThreeState.Error(message, response.code())
        }
    }
}