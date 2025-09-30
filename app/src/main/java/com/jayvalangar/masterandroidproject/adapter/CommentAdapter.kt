package com.jayvalangar.masterandroidproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jayvalangar.masterandroidproject.databinding.ItemCommentBinding
import com.jayvalangar.masterandroidproject.model.CommentFromEmployeeID


// This is a helper to show a list of comments in a scrollable view.
// It uses ListAdapter to make updates smooth.
class CommentAdapter : ListAdapter<CommentFromEmployeeID, CommentAdapter.ViewHolder>(DiffCallback) {


    // This class holds each comment item in the list.
    class ViewHolder(private val binding: ItemCommentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        // Fills the comment item with data like name, email, and message.
        fun bind(commentFromEmployeeID: CommentFromEmployeeID) {
            binding.commentNameText.text = commentFromEmployeeID.name
            binding.commentEmailText.text = commentFromEmployeeID.email
            binding.commentBodyText.text = commentFromEmployeeID.body
        }
    }

    // Creates a new view holder for each comment item when needed.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }


    // Fills the view holder with data for the right position in the list.
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    // A companion object to help compare old and new comment lists.
    companion object DiffCallback : DiffUtil.ItemCallback<CommentFromEmployeeID>() {

        // Checks if two comments are the same by their ID.
        override fun areItemsTheSame(
            oldItem: CommentFromEmployeeID,
            newItem: CommentFromEmployeeID
        ): Boolean {
            return oldItem.id == newItem.id
        }


        // Checks if the details of two comments are the same.
        override fun areContentsTheSame(
            oldItem: CommentFromEmployeeID,
            newItem: CommentFromEmployeeID
        ): Boolean {
            return oldItem == newItem
        }
    }
}