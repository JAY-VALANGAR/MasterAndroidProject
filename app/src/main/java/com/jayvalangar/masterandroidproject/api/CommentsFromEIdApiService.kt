package com.jayvalangar.masterandroidproject.api

import com.jayvalangar.masterandroidproject.model.CommentFromEmployeeID
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


// This is an interface that tells the app how to ask the internet for comments.
// It acts like a bridge to get data from a website.
interface CommentsFromEIdApiService {

    // Asks the website for a list of comments for a specific employee ID.
    // Runs in the background so the app doesn’t freeze.
    @GET("comments")
    suspend fun getComments(@Query("postId") postId: Int): Response<List<CommentFromEmployeeID>>
}