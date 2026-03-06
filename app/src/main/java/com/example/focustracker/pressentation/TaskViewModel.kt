package com.example.focustracker.pressentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.focustracker.data.TaskRepository
import com.example.focustracker.data.mapper.toTaskEntityDbModel
import com.example.focustracker.domain.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TaskViewModel(private val repository: TaskRepository) : ViewModel() {

    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    private val _history = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks
    val history: StateFlow<List<Task>> = _history

    init {
        loadTasks()
        loadCompleteTask()
    }

    fun addTask(name: String) {
        viewModelScope.launch {
            val task = Task(_tasks.value.size, name = name, isDone = false)
            _tasks.value += task
            repository.insertTask(task.toTaskEntityDbModel())
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch {
            _tasks.value = _tasks.value.map {
                if (it.id == task.id) {
                    it.copy(isDone = true)
                } else it
            }
            repository.updateTask(task.toTaskEntityDbModel())
        }
    }

    private fun loadTasks() {
        viewModelScope.launch {
            _tasks.value = repository.getAllTaskList()
        }
    }


    private fun loadCompleteTask() {
        viewModelScope.launch {
            _history.value = repository.getCompletedTasks()
        }
    }

    fun addCompletedTask(task: Task) {
        if (!_history.value.contains(task)) {
            _history.value += task
        }
        Log.d("TaskViewModel", "addCompletedTask")
    }


    fun deleteTask(task: Task) {
        viewModelScope.launch {
            _tasks.value -= task
            repository.deleteTask(task.toTaskEntityDbModel())
        }
    }

    fun deleteHistoryTask(task: Task) {
        _history.value -= task
    }
}