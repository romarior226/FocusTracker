    package com.example.focustracker.pressentation

    import android.util.Log
    import androidx.lifecycle.ViewModel
    import androidx.lifecycle.viewModelScope
    import com.example.focustracker.domain.entity.Task
    import com.example.focustracker.domain.usecases.AddHistoryUseCase
    import com.example.focustracker.domain.usecases.AddTaskUseCase
    import com.example.focustracker.domain.usecases.DeleteHistoryUseCase
    import com.example.focustracker.domain.usecases.DeleteTaskUseCase
    import com.example.focustracker.domain.usecases.GetHistoryUseCase
    import com.example.focustracker.domain.usecases.GetNetworkTasksCountUseCase
    import com.example.focustracker.domain.usecases.GetTasksUseCase
    import com.example.focustracker.domain.usecases.LoadNetworkTasksUseCase
    import com.example.focustracker.domain.usecases.UpdateTaskUseCase
    import dagger.hilt.android.lifecycle.HiltViewModel
    import kotlinx.coroutines.flow.MutableStateFlow
    import kotlinx.coroutines.flow.StateFlow
    import kotlinx.coroutines.launch
    import javax.inject.Inject

    @HiltViewModel
    class TaskViewModel @Inject constructor(
        private val addTaskUseCase: AddTaskUseCase,
        private val deleteTaskUseCase: DeleteTaskUseCase,
        private val updateTaskUseCase: UpdateTaskUseCase,
        private val getTasksUseCase: GetTasksUseCase,
        private val loadNetworkTasksUseCase: LoadNetworkTasksUseCase,
        private val getHistoryUseCase: GetHistoryUseCase,
        private val addHistoryUseCase: AddHistoryUseCase,
        private val getNetworkTasksCountUseCase: GetNetworkTasksCountUseCase,
        private val deleteHistoryUseCase: DeleteHistoryUseCase
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
                val task = addTaskUseCase(name)
                _tasks.value += task
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
                updateTaskUseCase(updatedTask)

            }
        }

        private suspend fun loadNetworkTasks() {
            loadTasks()
            try {
                loadNetworkTasksUseCase()
                loadTasks()
            } catch (e: Exception) {
                Log.e("TaskViewModel", "loadNetworkTasks error: ${e.message}")
            }
        }

        private suspend fun loadTasks() {
            _tasks.value = getTasksUseCase()
        }

        private suspend fun loadCompleteTask() {
            val result = getHistoryUseCase()
            _history.value = result
        }

        fun addCompletedTask(task: Task) {
            viewModelScope.launch {
                if (!_history.value.any { it.id == task.id }) {
                    _history.value += task
                    addHistoryUseCase(task)
                }
                Log.d("TaskViewModel", "addCompletedTask")
            }
        }

        fun deleteTask(task: Task) {
            viewModelScope.launch {
                _tasks.value -= task
                deleteTaskUseCase(task)
            }
        }

        fun deleteHistoryTask(history: Task) {
            viewModelScope.launch {
                _history.value -= history
                deleteHistoryUseCase(history)
            }
        }
    }