package com.jayvalangar.masterandroidproject.viewmodel.comment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jayvalangar.masterandroidproject.model.CommentFromEmployeeID
import com.jayvalangar.masterandroidproject.repository.CommentRepository
import com.jayvalangar.masterandroidproject.util.ApiThreeState
import kotlinx.coroutines.launch

// This is a helper that manages comment data for a screen.
// It keeps the data safe even if the phone rotates.
//--------------------------------------------------------------------------------------------
class CommentViewModel(private val repository: CommentRepository) : ViewModel() {

    // A private tool to hold comment data that can change.
    private val _comments = MutableLiveData<ApiThreeState<List<CommentFromEmployeeID>>>()

    // A public tool to let the screen watch for comment data changes.
    val comments: LiveData<ApiThreeState<List<CommentFromEmployeeID>>> = _comments


    // Gets comments for a specific employee when called.
    //--------------------------------------------------------------------------------------------
    fun fetchComments(postId: Int) {
        viewModelScope.launch {
            _comments.value = repository.getComments(postId)
        }
    }
    //--------------------------------------------------------------------------------------------

}
//--------------------------------------------------------------------------------------------