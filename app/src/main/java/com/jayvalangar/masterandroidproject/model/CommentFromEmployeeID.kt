package com.jayvalangar.masterandroidproject.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CommentFromEmployeeID(
    val postId: Int,
    val id: Int,
    val name: String,
    val email: String,
    val body: String
) : Parcelable