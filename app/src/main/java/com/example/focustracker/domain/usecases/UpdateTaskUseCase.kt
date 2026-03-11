package com.example.focustracker.domain.usecases

import com.example.focustracker.domain.entity.Task
import com.example.focustracker.domain.repo.TaskRepositoryInterface
import javax.inject.Inject

class UpdateTaskUseCase @Inject constructor(private val taskRepositoryInterface: TaskRepositoryInterface) {
    suspend operator fun invoke(task: Task) {
        taskRepositoryInterface.updateTask(task)
    }
}
