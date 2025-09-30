package com.jayvalangar.masterandroidproject.repository


import android.content.Context
import com.jayvalangar.masterandroidproject.util.ApiResponseHandler
import com.jayvalangar.masterandroidproject.util.RetrofitInstance
import com.jayvalangar.masterandroidproject.model.CommentFromEmployeeID
import com.jayvalangar.masterandroidproject.util.ApiThreeState
import com.jayvalangar.masterandroidproject.common.NetworkUtils


// This is a helper class to get comments for an employee from the internet.
// It uses the phone’s context to check the network.
class CommentRepository(private val context: Context) {

    // Gets a list of comments for a specific employee ID.
    // Runs in the background so the app doesn’t freeze.
    suspend fun getComments(postId: Int): ApiThreeState<List<CommentFromEmployeeID>> {
        // Checks if the phone has internet. If not, returns an error.
        if (!NetworkUtils.isNetworkAvailable(context)) {
            return ApiThreeState.Error("No internet connection")
        }
        // Tries to get the comments from the internet.
        return try {
            // Asks the internet tool to get comments for the given ID.
            val response = RetrofitInstance.commentsApi.getComments(postId)
            // Checks the response and turns it into a simple state (success or error).
            ApiResponseHandler.handleResponse(response)
        } catch (e: Exception) {
            // If something goes wrong (like no signal), returns an error with the problem.
            ApiThreeState.Error("Network error: ${e.message}")
        }
    }
}