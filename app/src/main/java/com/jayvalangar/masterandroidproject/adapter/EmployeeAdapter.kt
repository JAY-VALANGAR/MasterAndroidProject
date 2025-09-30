package com.jayvalangar.masterandroidproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.jayvalangar.masterandroidproject.databinding.ItemEmployeeListBinding
import com.jayvalangar.masterandroidproject.model.Employee


// This is a helper to show a list of employees in a scrollable view.
// It uses ListAdapter to make updates smooth and lets users click an employee.
class EmployeeAdapter(private val onClick: (Employee) -> Unit) :
    ListAdapter<Employee, EmployeeAdapter.ViewHolder>(DiffCallback) {


    // This class holds each employee item in the list.
    class ViewHolder(private val binding: ItemEmployeeListBinding) :
        RecyclerView.ViewHolder(binding.root) {


        // Fills the employee item with data like picture, name, and department, and handles clicks.
        fun bind(employee: Employee, onClick: (Employee) -> Unit) {
            binding.profileImage.load(employee.profile)
            binding.nameText.text = employee.name
            binding.departmentText.text = employee.department
            binding.root.setOnClickListener { onClick(employee) }
        }
    }


    // Creates a new view holder for each employee item when needed.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Makes a new layout for an employee item.
        val binding =
            ItemEmployeeListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }


    // Fills the view holder with data for the right position in the list.
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), onClick)
    }


    // A companion object to help compare old and new employee lists.
    companion object DiffCallback : DiffUtil.ItemCallback<Employee>() {
        // Checks if two employees are the same by their ID.
        override fun areItemsTheSame(oldItem: Employee, newItem: Employee): Boolean {
            return oldItem.id == newItem.id
        }

        // Checks if the details of two employees are the same.
        override fun areContentsTheSame(oldItem: Employee, newItem: Employee): Boolean {
            return oldItem == newItem
        }
    }
}