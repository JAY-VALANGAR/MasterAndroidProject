package com.jayvalangar.masterandroidproject.util

import retrofit2.Response

// This is a helper object to check and handle responses from the internet.
// It figures out if the data came successfully or if there was an error.
//-------------------------------------------------------------------------------------------
object ApiResponseHandler {

    // Takes a response from the internet and turns it into a simple state.
//-------------------------------------------------------------------------------------------
    fun <T> handleResponse(response: Response<T>): ApiThreeState<T> {

        // Checks if the response was successful (no errors).
        return if (response.isSuccessful) {

            // If there’s data, returns it as a success state.
            // If no data, returns an error state.
            response.body()?.let { ApiThreeState.Success(it) }
                ?: ApiThreeState.Error("No data received", response.code())
        } else {

            // If there’s an error, picks the right message based on the error code.
            val message = when (response.code()) {
                400 -> "Bad Request: Invalid input" // Wrong input sent.
                401 -> "Unauthorized: Authentication failed" // Login failed.
                403 -> "Forbidden: Access denied" // Not allowed to see this.
                404 -> "Not Found: Resource not available" // Data not found.
                406 -> "Not Acceptable: Invalid format" // Wrong data format.
                413 -> "Payload Too Large: Request too big" // Data sent is too big.
                429 -> "Too Many Requests: Rate limit exceeded" // Too many tries.
                500 -> "Internal Server Error: Server issue" // Server problem.
                501 -> "Not Implemented: Feature not supported" // Feature not ready.
                502 -> "Bad Gateway: Invalid response from upstream" // Bad server reply.
                503 -> "Service Unavailable: Server overloaded" // Server too busy.
                504 -> "Gateway Timeout: Server took too long" // Server too slow.
                else -> "Unknown error (Code: ${response.code()})" // Unknown problem.
            }
            // Returns an error state with the message and error code.
            ApiThreeState.Error(message, response.code())
        }
    }
//-------------------------------------------------------------------------------------------
}