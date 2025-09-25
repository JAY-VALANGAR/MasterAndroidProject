package com.jayvalangar.masterandroidproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.jayvalangar.masterandroidproject.databinding.ItemEmployeeListBinding
import com.jayvalangar.masterandroidproject.model.Employee

class EmployeeAdapter(private val onClick: (Employee) -> Unit) :
    ListAdapter<Employee, EmployeeAdapter.ViewHolder>(DiffCallback) {

    class ViewHolder(private val binding: ItemEmployeeListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(employee: Employee, onClick: (Employee) -> Unit) {
            binding.profileImage.load(employee.profile)
            binding.nameText.text = employee.name
            binding.departmentText.text = employee.department
            binding.root.setOnClickListener { onClick(employee) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemEmployeeListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), onClick)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Employee>() {
        override fun areItemsTheSame(oldItem: Employee, newItem: Employee): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Employee, newItem: Employee): Boolean {
            return oldItem == newItem
        }
    }
}