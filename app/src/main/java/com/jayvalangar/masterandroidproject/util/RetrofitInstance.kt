package com.jayvalangar.masterandroidproject.util

import com.jayvalangar.masterandroidproject.api.CommentsFromEIdApiService
import com.jayvalangar.masterandroidproject.api.EmployeeApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


// This is a helper object to set up connections to get data from the internet.
//--------------------------------------------------------------------------
object RetrofitInstance {
    // The main address to get employee data from.
    const val EMPLOYEE_BASE_URL = "https://mp9fb89bd89a3988feac.free.beeceptor.com/"

    // The main address to get comment data from.
    const val COMMENTS_BASE_URL = "https://jsonplaceholder.typicode.com/"

    // Makes a connection tool with the given address.
//--------------------------------------------------------------------------
    private fun getRetrofit(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl) // Sets the address to connect to.
            .addConverterFactory(GsonConverterFactory.create()) // Turns internet data into app data.
            .build() // Finishes making the connection tool.
    }
//--------------------------------------------------------------------------


    // Creates a tool to get employee data, only when needed.
//--------------------------------------------------------------------------
    val employeeApi: EmployeeApiService by lazy {
        getRetrofit(EMPLOYEE_BASE_URL).create(EmployeeApiService::class.java)
    }
//--------------------------------------------------------------------------


    // Creates a tool to get comment data, only when needed.
//--------------------------------------------------------------------------
    val commentsApi: CommentsFromEIdApiService by lazy {
        getRetrofit(COMMENTS_BASE_URL).create(CommentsFromEIdApiService::class.java)
    }
//--------------------------------------------------------------------------


}
//--------------------------------------------------------------------------