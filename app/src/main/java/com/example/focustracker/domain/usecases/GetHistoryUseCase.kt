package com.example.focustracker.domain.usecases

import com.example.focustracker.domain.repo.HistoryRepositoryInterface
import com.example.focustracker.domain.entity.Task
import javax.inject.Inject

class GetHistoryUseCase @Inject constructor(private val historyRepository: HistoryRepositoryInterface) {
    suspend operator fun invoke(): List<Task> {
        return historyRepository.getAllHistoryList()
    }
}