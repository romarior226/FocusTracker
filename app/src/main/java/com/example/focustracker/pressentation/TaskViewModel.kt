package com.example.focustracker.pressentation

import androidx.lifecycle.ViewModel
import com.example.focustracker.domain.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.count

class TaskViewModel : ViewModel() {
    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks
    fun addTask(name: String) {
        _tasks.value += Task(_tasks.value.size, name = name, isDone = false)
    }

    fun deleteTask(task: Task) {
        _tasks.value -= task
    }
}