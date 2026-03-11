package com.example.focustracker.domain.repo

import com.example.focustracker.domain.entity.Task

interface HistoryRepositoryInterface {
    suspend fun getAllHistoryList(): List<Task>
    suspend fun insertHistory(history: Task): Long
    suspend fun deleteHistory(history: Task)
}