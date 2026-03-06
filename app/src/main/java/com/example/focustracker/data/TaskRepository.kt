package com.example.focustracker.data

import com.example.focustracker.data.dao.TaskDao
import com.example.focustracker.data.entity.TaskEntityDbModel
import com.example.focustracker.data.mapper.toTask
import com.example.focustracker.domain.Task

class TaskRepository(private val taskDao: TaskDao) {
    suspend fun getAllTaskEntityDbModelList(): List<TaskEntityDbModel> {
        return taskDao.getAllTasks()
    }

    suspend fun getAllTaskList(): List<Task> {
        return getAllTaskEntityDbModelList()
            .map {
                it.toTask()
            }
    }

    suspend fun getCompletedTasksEntityDbModelList(): List<TaskEntityDbModel> {
        return taskDao.getCompletedTasks()
    }

    suspend fun getCompletedTasks(): List<Task> {
        return getCompletedTasksEntityDbModelList()
            .map {
                it.toTask()
            }
    }

    suspend fun updateTask(taskEntityDbModel: TaskEntityDbModel) {
        taskDao.updateTask(taskEntityDbModel)
    }

    suspend fun insertTask(taskEntityDbModel: TaskEntityDbModel) {
        taskDao.insertTask(taskEntityDbModel)
    }

    suspend fun deleteTask(taskEntityDbModel: TaskEntityDbModel) {
        taskDao.deleteTask(taskEntityDbModel)
    }

}