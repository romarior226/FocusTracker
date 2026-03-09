package com.example.focustracker.pressentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.focustracker.databinding.ItemTaskBinding
import com.example.focustracker.domain.Task
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TaskAdapter(
    private val isCheckboxEnabled: Boolean = true,
    private val onDeleteClick: ((Task) -> Unit)? = null,
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
            cbTaskDone.isEnabled = isCheckboxEnabled
            val format = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
            tvCreatedAt.text = "Створено: ${format.format(Date(item.timeCreation))}"
            if(item.completedTime != null) {
                tvCompletedAt.visibility = View.VISIBLE
                tvCompletedAt.text = "Виконано : ${format.format(Date(item.completedTime))}"
            }
            if (item.isFromNetwork) {
                tvTaskName.text = "\uD83C\uDF10 ${item.name}"
            } else tvTaskName.text = item.name
            cbTaskDone.isChecked = item.isDone
            btnDelete.setOnClickListener {
                onDeleteClick?.invoke(item)
            }
            cbTaskDone.setOnClickListener {
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