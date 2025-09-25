package com.jayvalangar.masterandroidproject.api

import com.jayvalangar.masterandroidproject.model.EmployeeResponse
import retrofit2.http.GET
import retrofit2.Response

interface EmployeeApiService {
    @GET("employeedetails")
    suspend fun getEmployees(): Response<EmployeeResponse>
}