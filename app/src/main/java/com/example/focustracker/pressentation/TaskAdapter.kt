package com.example.focustracker.pressentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.focustracker.databinding.ItemTaskBinding
import com.example.focustracker.domain.Task

class TaskAdapter(
    private val onDeleteClick: ((Task) -> Unit)? = null,
    private val onChecked: ((Task) -> Unit)? = null,
    private val updateTasks: ((Task) -> Unit)? = null
) : ListAdapter<Task, TaskAdapter.TaskViewHolder>(DIFF_CALLBACK) {

    class TaskViewHolder(val binding: ItemTaskBinding) :
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
            btnDelete.setOnClickListener {
                onDeleteClick?.invoke(item)
            }
            cbTaskDone.setOnClickListener {
                onChecked?.invoke(item)
                updateTasks?.invoke(item)

            }

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