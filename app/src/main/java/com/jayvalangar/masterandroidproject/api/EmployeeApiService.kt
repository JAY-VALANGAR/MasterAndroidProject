package com.jayvalangar.masterandroidproject.api

import com.jayvalangar.masterandroidproject.model.EmployeeResponse
import retrofit2.http.GET
import retrofit2.Response


// This is an interface that tells the app how to ask the internet for employee data.
// It acts like a bridge to get data from a website.
interface EmployeeApiService {


    // Asks the website for a list of employees.
    // Runs in the background so the app doesn’t freeze.
    @GET("employeedetails")
    suspend fun getEmployees(): Response<EmployeeResponse>
}