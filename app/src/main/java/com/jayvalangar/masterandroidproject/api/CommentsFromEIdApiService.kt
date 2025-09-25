package com.jayvalangar.masterandroidproject.api

import com.jayvalangar.masterandroidproject.model.CommentFromEmployeeID
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CommentsFromEIdApiService {
    @GET("comments")
    suspend fun getComments(@Query("postId") postId: Int): Response<List<CommentFromEmployeeID>>
}