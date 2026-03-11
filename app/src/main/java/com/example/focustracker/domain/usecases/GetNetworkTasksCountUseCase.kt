package com.example.focustracker.domain.usecases

import com.example.focustracker.domain.repo.TaskRepositoryInterface
import javax.inject.Inject

class GetNetworkTasksCountUseCase @Inject constructor(private val taskRepositoryInterface: TaskRepositoryInterface) {
    suspend operator fun invoke() : Int {
        return taskRepositoryInterface.getNetworkTasksCount()
    }
}