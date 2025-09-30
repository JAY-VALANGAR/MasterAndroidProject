package com.jayvalangar.masterandroidproject.viewmodel.employee

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jayvalangar.masterandroidproject.model.EmployeeResponse
import com.jayvalangar.masterandroidproject.repository.EmployeeRepository
import com.jayvalangar.masterandroidproject.util.ApiThreeState
import kotlinx.coroutines.launch


// This is a helper that manages employee data for a screen.
// It keeps the data safe even if the phone rotates.
//----------------------------------------------------------------------------------------------
class EmployeeViewModel(private val repository: EmployeeRepository) : ViewModel() {

    // A private tool to hold employee data that can change.
    private val _employees = MutableLiveData<ApiThreeState<EmployeeResponse>>()

    // A public tool to let the screen watch for employee data changes.
    val employees: LiveData<ApiThreeState<EmployeeResponse>> = _employees


    // Runs when the ViewModel starts to get employee data right away.
    init {
        fetchEmployees()
    }

    // Gets the list of employees when called.
    fun fetchEmployees() {
        // Runs the data fetch in the background so the app doesn’t freeze.
        viewModelScope.launch {
            // Updates the employee data with what the repository finds.
            _employees.value = repository.getEmployees()
        }
    }
}
//----------------------------------------------------------------------------------------------