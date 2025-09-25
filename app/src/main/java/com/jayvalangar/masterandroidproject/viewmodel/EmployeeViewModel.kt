package com.jayvalangar.masterandroidproject.viewmodel.employee

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jayvalangar.masterandroidproject.model.EmployeeResponse
import com.jayvalangar.masterandroidproject.repository.EmployeeRepository
import com.jayvalangar.masterandroidproject.util.ApiThreeState
import kotlinx.coroutines.launch

class EmployeeViewModel(private val repository: EmployeeRepository) : ViewModel() {

    private val _employees = MutableLiveData<ApiThreeState<EmployeeResponse>>()
    val employees: LiveData<ApiThreeState<EmployeeResponse>> = _employees

    init {
        fetchEmployees()
    }

    fun fetchEmployees() {
        viewModelScope.launch {
            _employees.value = repository.getEmployees()
        }
    }
}