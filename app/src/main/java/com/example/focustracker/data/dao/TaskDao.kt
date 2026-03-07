package com.example.focustracker.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.focustracker.data.entity.TaskEntityDbModel


@Dao
interface TaskDao {

    @Query("SELECT * FROM tasks")
    suspend fun getAllTasks(): List<TaskEntityDbModel>
    @Insert
    suspend fun insertTask(taskEntityDbModel: TaskEntityDbModel): Long

    @Update
    suspend fun updateTask(taskEntityDbModel: TaskEntityDbModel)

    @Delete
    suspend fun deleteTask(taskEntityDbModel: TaskEntityDbModel)
}