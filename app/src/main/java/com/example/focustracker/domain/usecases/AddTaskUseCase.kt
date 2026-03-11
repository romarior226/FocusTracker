package com.example.focustracker.domain.usecases

import com.example.focustracker.domain.entity.Task
import com.example.focustracker.domain.repo.TaskRepositoryInterface
import javax.inject.Inject

class AddTaskUseCase @Inject constructor(private val taskRepository: TaskRepositoryInterface) {
    suspend operator fun invoke(name: String): Task {
        val task = Task(
            id = 0,
            name = name,
            isDone = false,
            timeCreation = System.currentTimeMillis(),
            completedTime = null
        )
        val generatedId = taskRepository.insertTask(task)
        return task.copy(id = generatedId.toInt())
    }
}