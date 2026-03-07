package com.example.focustracker.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.focustracker.data.entity.HistoryEntityDbModel
import com.example.focustracker.data.entity.TaskEntityDbModel

@Dao
interface HistoryDao {
    @Query("SELECT * FROM history")

    suspend fun getAllHistory(): List<HistoryEntityDbModel>

    @Insert
    suspend fun insertHistory(historyEntityDbModel: HistoryEntityDbModel): Long


    @Delete
    suspend fun deleteHistory(historyEntityDbModel: HistoryEntityDbModel)
}