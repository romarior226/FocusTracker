package com.example.focustracker.pressentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.focustracker.data.HistoryRepository
import com.example.focustracker.data.TaskRepository

class TaskViewModelFactory(private val taskRepository: TaskRepository,
    private val historyRepository: HistoryRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TaskViewModel(taskRepository , historyRepository) as T
    }
}