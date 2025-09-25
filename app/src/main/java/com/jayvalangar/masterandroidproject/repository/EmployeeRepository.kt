package com.jayvalangar.masterandroidproject.repository

import android.content.Context
import com.jayvalangar.masterandroidproject.util.RetrofitInstance
import com.jayvalangar.masterandroidproject.model.CommentFromEmployeeID
import com.jayvalangar.masterandroidproject.model.EmployeeResponse
import com.jayvalangar.masterandroidproject.util.ApiResponseHandler
import com.jayvalangar.masterandroidproject.common.NetworkUtils
import com.jayvalangar.masterandroidproject.util.ApiThreeState
import retrofit2.Response

class EmployeeRepository(private val context: Context) {

    suspend fun getEmployees(): ApiThreeState<EmployeeResponse> {
        if (!NetworkUtils.isNetworkAvailable(context)) {
            return ApiThreeState.Error("No internet connection")
        }
        return try {
            val response = RetrofitInstance.employeeApi.getEmployees()
            ApiResponseHandler.handleResponse(response)
        } catch (e: Exception) {
            ApiThreeState.Error("Network error: ${e.message}")
        }
    }
}