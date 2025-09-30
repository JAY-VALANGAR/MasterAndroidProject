package com.jayvalangar.masterandroidproject.viewmodel.comment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jayvalangar.masterandroidproject.repository.CommentRepository


// This is a factory that helps create the CommentViewModel with the right tools.
class CommentViewModelFactory(
    private val repository: CommentRepository
) : ViewModelProvider.Factory {


    // Makes a new CommentViewModel when needed.
    //----------------------------------------------------------------------------------------------
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        // Checks if the requested ViewModel is the CommentViewModel.
        if (modelClass.isAssignableFrom(CommentViewModel::class.java)) {

            // Creates and returns a new CommentViewModel with the repository.
            @Suppress("UNCHECKED_CAST")
            return CommentViewModel(repository) as T
        }

        // Throws an error if the wrong ViewModel is asked for.
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
    //----------------------------------------------------------------------------------------------

}