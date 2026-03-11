package com.example.focustracker.domain.usecases

import com.example.focustracker.domain.entity.Task
import com.example.focustracker.domain.repo.TaskRepositoryInterface
import com.example.focustracker.domain.repo.TodoRepositoryInterface
import javax.inject.Inject

class LoadNetworkTasksUseCase @Inject constructor(
    private val todoRepositoryInterface: TodoRepositoryInterface,
    private val taskRepositoryInterface: TaskRepositoryInterface
) {
    suspend operator fun invoke() {
        val count = taskRepositoryInterface.getNetworkTasksCount()
        if (count == 0) {
            val todos = todoRepositoryInterface.getTodos()
            todos.forEach {
                val task = Task(
                    id = 0,
                    name = "🌐 ${it.title}",
                    isDone = it.completed,
                    timeCreation = System.currentTimeMillis(),
                    completedTime = null,
                    isFromNetwork = true
                )
                taskRepositoryInterface.insertTask(task)
            }
        }
    }
}