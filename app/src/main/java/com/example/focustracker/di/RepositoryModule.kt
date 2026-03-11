package com.example.focustracker.di

import com.example.focustracker.data.HistoryRepository
import com.example.focustracker.data.TaskRepository
import com.example.focustracker.data.TodoRepository
import com.example.focustracker.domain.repo.HistoryRepositoryInterface
import com.example.focustracker.domain.repo.TaskRepositoryInterface
import com.example.focustracker.domain.repo.TodoRepositoryInterface
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn

import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindTaskRepository(
        taskRepository: TaskRepository
    ): TaskRepositoryInterface

    @Binds
    abstract fun bindHistoryRepository(
        historyRepository: HistoryRepository
    ): HistoryRepositoryInterface

    @Binds
    abstract fun bindTodoRepository(
        todoRepository: TodoRepository
    ):  TodoRepositoryInterface
}