package com.example.focustracker.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.focustracker.data.entity.TaskEntityDbModel


@Dao
interface TaskDao {

    @Query("SELECT COUNT(*) FROM tasks WHERE isFromNetwork = 1")
    suspend fun getNetworkTasksCount(): Int

    @Query("SELECT * FROM tasks")
    suspend fun getAllTasks(): List<TaskEntityDbModel>

    @Insert
    suspend fun insertTask(taskEntityDbModel: TaskEntityDbModel): Long

    @Update
    suspend fun updateTask(taskEntityDbModel: TaskEntityDbModel)

    @Delete
    suspend fun deleteTask(taskEntityDbModel: TaskEntityDbModel)
}