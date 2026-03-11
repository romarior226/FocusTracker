package com.example.focustracker.domain.repo

import com.example.focustracker.domain.entity.Task

interface TaskRepositoryInterface {
    suspend fun getAllTaskList(): List<Task>
    suspend fun insertTask(task: Task): Long
    suspend fun updateTask(task: Task)
    suspend fun deleteTask(task: Task)
    suspend fun getNetworkTasksCount(): Int
}