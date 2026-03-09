package com.example.focustracker.pressentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.focustracker.data.HistoryRepository
import com.example.focustracker.data.TaskRepository
import com.example.focustracker.data.TodoRepository
import com.example.focustracker.data.mapper.toHistoryEntityDbModel
import com.example.focustracker.data.mapper.toEntityDbModel
import com.example.focustracker.domain.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TaskViewModel(
    private val taskRepository: TaskRepository,
    private val historyRepository: HistoryRepository,
    private val todoRepository: TodoRepository
) : ViewModel() {

    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    private val _history = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks
    val history: StateFlow<List<Task>> = _history

    init {
        viewModelScope.launch {
            loadCompleteTask()
            loadNetworkTasks()
            loadTasks()
        }
    }

    fun addTask(name: String) {
        viewModelScope.launch {
            val task = Task(
                id = 0,
                name = name,
                isDone = false,
                timeCreation = System.currentTimeMillis(),
                completedTime = null
            )
            val generatedId = taskRepository.insertTask(task.toEntityDbModel())
            _tasks.value += task.copy(id = generatedId.toInt())
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch {
            val updatedTask =
                task.copy(isDone = !task.isDone, completedTime = System.currentTimeMillis())
            Log.d("TaskViewModel", "updateTask isDone: ${updatedTask.isDone}")
            _tasks.value = _tasks.value.map {
                if (it.id == updatedTask.id) {
                    it.copy(isDone = updatedTask.isDone)
                } else it

            }
            if (updatedTask.isDone) {
                addCompletedTask(updatedTask)
            }
            taskRepository.updateTask(updatedTask.toEntityDbModel())

        }
    }

    private suspend fun loadNetworkTasks() {
        loadTasks()
        val count = taskRepository.getNetworkTasksCount()
        Log.d("TaskViewModel", "networkTasksCount: $count")
        if (count == 0) {
            try {
                val todos = todoRepository.getTodos()
                todos.forEach {
                    val task = Task(
                        id = 0,
                        name = "🌐 ${it.title}",
                        isDone = it.completed,
                        timeCreation = System.currentTimeMillis(),
                        completedTime = null,
                        isFromNetwork = true
                    )
                    taskRepository.insertTask(task.toEntityDbModel())
                }
                loadTasks()
            } catch (e: Exception) {
                Log.e("TaskViewModel", "loadNetworkTasks error: ${e.message}")
            }
        }
    }

    private suspend fun loadTasks() {
        _tasks.value = taskRepository.getAllTaskList()
    }

    private suspend fun loadCompleteTask() {
        val result = historyRepository.getAllHistoryList()
        _history.value = result
    }

    fun addCompletedTask(task: Task) {
        viewModelScope.launch {
            if (!_history.value.any { it.id == task.id }) {
                _history.value += task
                historyRepository.insertHistory(task.toHistoryEntityDbModel())
            }
            Log.d("TaskViewModel", "addCompletedTask")
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            _tasks.value -= task
            taskRepository.deleteTask(task.toEntityDbModel())
        }
    }

    fun deleteHistoryTask(task: Task) {
        viewModelScope.launch {
            _history.value -= task
            historyRepository.deleteHistory(task.toHistoryEntityDbModel())
        }
    }
}