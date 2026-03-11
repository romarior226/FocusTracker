package com.example.focustracker.domain.usecases

import com.example.focustracker.domain.entity.Task
import com.example.focustracker.domain.repo.TaskRepositoryInterface
import javax.inject.Inject

class DeleteTaskUseCase @Inject constructor(private val taskRepositoryInterface: TaskRepositoryInterface) {
    suspend operator fun invoke(task: Task) {
        taskRepositoryInterface.deleteTask(task)
    }
}