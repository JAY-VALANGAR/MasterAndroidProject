package com.jayvalangar.masterandroidproject.viewmodel.comment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jayvalangar.masterandroidproject.model.CommentFromEmployeeID
import com.jayvalangar.masterandroidproject.repository.CommentRepository
import com.jayvalangar.masterandroidproject.util.ApiThreeState
import kotlinx.coroutines.launch

class CommentViewModel(private val repository: CommentRepository) : ViewModel() {

    private val _comments = MutableLiveData<ApiThreeState<List<CommentFromEmployeeID>>>()
    val comments: LiveData<ApiThreeState<List<CommentFromEmployeeID>>> = _comments

    fun fetchComments(postId: Int) {
        viewModelScope.launch {
            _comments.value = repository.getComments(postId)
        }
    }
}