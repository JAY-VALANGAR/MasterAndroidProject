package com.jayvalangar.masterandroidproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jayvalangar.masterandroidproject.databinding.ItemCommentBinding
import com.jayvalangar.masterandroidproject.model.CommentFromEmployeeID

class CommentAdapter : ListAdapter<CommentFromEmployeeID, CommentAdapter.ViewHolder>(DiffCallback) {

    class ViewHolder(private val binding: ItemCommentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(commentFromEmployeeID: CommentFromEmployeeID) {
            binding.commentNameText.text = commentFromEmployeeID.name
            binding.commentEmailText.text = commentFromEmployeeID.email
            binding.commentBodyText.text = commentFromEmployeeID.body
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object DiffCallback : DiffUtil.ItemCallback<CommentFromEmployeeID>() {
        override fun areItemsTheSame(
            oldItem: CommentFromEmployeeID,
            newItem: CommentFromEmployeeID
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: CommentFromEmployeeID,
            newItem: CommentFromEmployeeID
        ): Boolean {
            return oldItem == newItem
        }
    }
}