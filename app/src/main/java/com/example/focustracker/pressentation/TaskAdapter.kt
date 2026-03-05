package com.example.focustracker.pressentation

import android.text.Layout
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.focustracker.databinding.FragmentTimerBinding
import com.example.focustracker.databinding.ItemTaskBinding
import com.example.focustracker.domain.Task

class TaskAdapter : ListAdapter<Task, TaskAdapter.TaskViewHolder>(DIFF_CALLBACK) {

    class TaskViewHolder( val binding: ItemTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TaskViewHolder {
        val binding = ItemTaskBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            tvTaskName.text = item.name
            cbTaskDone.isChecked = item.isDone


        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Task>() {
            override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
                return oldItem == newItem
            }
        }
    }
}