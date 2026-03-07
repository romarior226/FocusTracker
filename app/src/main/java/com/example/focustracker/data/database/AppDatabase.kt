package com.example.focustracker.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.focustracker.data.dao.HistoryDao
import com.example.focustracker.data.dao.TaskDao
import com.example.focustracker.data.entity.HistoryEntityDbModel
import com.example.focustracker.data.entity.TaskEntityDbModel

@Database(entities = [TaskEntityDbModel::class , HistoryEntityDbModel::class], version = 4)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
    abstract fun historyTaskDao() : HistoryDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDataBase(context: Context) : AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "focus_tracker_db"
                )
                    .fallbackToDestructiveMigration()
                    .build().also {
                    INSTANCE = it
                }
            }
        }
    }
}