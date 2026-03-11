package com.example.focustracker.data

import com.example.focustracker.data.database.dao.TaskDao
import com.example.focustracker.data.mapper.toEntityDbModel
import com.example.focustracker.data.mapper.toTask
import com.example.focustracker.domain.entity.Task
import com.example.focustracker.domain.repo.TaskRepositoryInterface
import javax.inject.Inject

class TaskRepository @Inject constructor(private val taskDao: TaskDao): TaskRepositoryInterface {

    override suspend fun getNetworkTasksCount(): Int {
        return taskDao.getNetworkTasksCount()
    }

    override suspend fun getAllTaskList(): List<Task> {
        return taskDao.getAllTasks().map { it.toTask() }
    }

    override suspend fun insertTask(task: Task): Long {
        return taskDao.insertTask(task.toEntityDbModel())
    }

    override suspend fun updateTask(task: Task) {
        taskDao.updateTask(task.toEntityDbModel())
    }

    override suspend fun deleteTask(task: Task) {
        taskDao.deleteTask(task.toEntityDbModel())
    }
}