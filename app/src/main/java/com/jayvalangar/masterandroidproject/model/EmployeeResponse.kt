package com.jayvalangar.masterandroidproject.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class EmployeeResponse(
    val status: String,
    val totalEmployees: Int,
    val employees: List<Employee>
) : Parcelable {

}

@Parcelize
data class Employee(
    val id: Int,
    val name: String,
    val email: String,
    val phone: String,
    val department: String,
    val designation: String,
    val salary: Int,
    val profile: String
) : Parcelable