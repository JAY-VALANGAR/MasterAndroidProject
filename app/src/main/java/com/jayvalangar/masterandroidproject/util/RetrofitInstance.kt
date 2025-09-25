package com.jayvalangar.masterandroidproject.util

import com.jayvalangar.masterandroidproject.api.CommentsFromEIdApiService
import com.jayvalangar.masterandroidproject.api.EmployeeApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    const val EMPLOYEE_BASE_URL = "https://mp9fb89bd89a3988feac.free.beeceptor.com/"
    const val COMMENTS_BASE_URL = "https://jsonplaceholder.typicode.com/"

    private fun getRetrofit(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val employeeApi: EmployeeApiService by lazy {
        getRetrofit(EMPLOYEE_BASE_URL).create(EmployeeApiService::class.java)
    }

    val commentsApi: CommentsFromEIdApiService by lazy {
        getRetrofit(COMMENTS_BASE_URL).create(CommentsFromEIdApiService::class.java)
    }
}