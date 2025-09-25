package com.jayvalangar.masterandroidproject.viewmodel.comment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jayvalangar.masterandroidproject.repository.CommentRepository

class CommentViewModelFactory(
    private val repository: CommentRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CommentViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CommentViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}