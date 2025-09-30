package com.jayvalangar.masterandroidproject.viewmodel.employee

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jayvalangar.masterandroidproject.repository.EmployeeRepository

// This is a factory that helps create the EmployeeViewModel with the right tools.
//--------------------------------------------------------------------------------------------
class EmployeeViewModelFactory(
    private val repository: EmployeeRepository
) : ViewModelProvider.Factory {

    // Makes a new EmployeeViewModel when needed.
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // Checks if the requested ViewModel is the EmployeeViewModel.
        if (modelClass.isAssignableFrom(EmployeeViewModel::class.java)) {
            // Creates and returns a new EmployeeViewModel with the repository.
            @Suppress("UNCHECKED_CAST")
            return EmployeeViewModel(repository) as T
        }
        // Throws an error if the wrong ViewModel is asked for.
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
//--------------------------------------------------------------------------------------------