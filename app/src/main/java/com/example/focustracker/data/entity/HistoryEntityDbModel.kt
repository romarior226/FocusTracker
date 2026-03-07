package com.example.focustracker.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history")
data class HistoryEntityDbModel(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val timeCreation: Long,
    val completedTime: Long?
)