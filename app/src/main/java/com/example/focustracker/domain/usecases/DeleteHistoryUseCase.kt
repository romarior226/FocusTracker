package com.example.focustracker.domain.usecases

import com.example.focustracker.domain.repo.HistoryRepositoryInterface
import com.example.focustracker.domain.entity.Task
import javax.inject.Inject

class DeleteHistoryUseCase @Inject constructor(private val historyRepository: HistoryRepositoryInterface) {
    suspend operator fun invoke(history: Task) {
        historyRepository.deleteHistory(history)
    }
}