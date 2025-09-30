package com.jayvalangar.masterandroidproject.repository

import android.content.Context
import com.jayvalangar.masterandroidproject.util.RetrofitInstance
import com.jayvalangar.masterandroidproject.model.CommentFromEmployeeID
import com.jayvalangar.masterandroidproject.model.EmployeeResponse
import com.jayvalangar.masterandroidproject.util.ApiResponseHandler
import com.jayvalangar.masterandroidproject.common.NetworkUtils
import com.jayvalangar.masterandroidproject.util.ApiThreeState
import retrofit2.Response


// This is a helper class to get a list of employees from the internet.
// It uses the phone’s context to check the network.
class EmployeeRepository(private val context: Context) {

    // Gets the list of employees.
    // Runs in the background so the app doesn’t freeze.
    suspend fun getEmployees(): ApiThreeState<EmployeeResponse> {

        // Checks if the phone has internet. If not, returns an error.
        if (!NetworkUtils.isNetworkAvailable(context)) {
            return ApiThreeState.Error("No internet connection")
        }

        // Tries to get the employees from the internet.
        return try {

            // Asks the internet tool to get the employee list.
            val response = RetrofitInstance.employeeApi.getEmployees()

            // Checks the response and turns it into a simple state (success or error).
            ApiResponseHandler.handleResponse(response)
        } catch (e: Exception) {

            // If something goes wrong (like no signal), returns an error with the problem.
            ApiThreeState.Error("Network error: ${e.message}")
        }
    }
}