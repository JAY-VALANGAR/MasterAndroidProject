package com.jayvalangar.masterandroidproject.repository


import android.content.Context
import com.jayvalangar.masterandroidproject.util.ApiResponseHandler
import com.jayvalangar.masterandroidproject.util.RetrofitInstance
import com.jayvalangar.masterandroidproject.model.CommentFromEmployeeID
import com.jayvalangar.masterandroidproject.util.ApiThreeState
import com.jayvalangar.masterandroidproject.common.NetworkUtils

class CommentRepository(private val context: Context) {

    suspend fun getComments(postId: Int): ApiThreeState<List<CommentFromEmployeeID>> {
        if (!NetworkUtils.isNetworkAvailable(context)) {
            return ApiThreeState.Error("No internet connection")
        }
        return try {
            val response = RetrofitInstance.commentsApi.getComments(postId)
            ApiResponseHandler.handleResponse(response)
        } catch (e: Exception) {
            ApiThreeState.Error("Network error: ${e.message}")
        }
    }
}