package com.example.focustracker.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "tasks")
data class TaskEntityDbModel(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val isDone: Boolean,
    val timeCreation: Long,
    val completedTime: Long?,
    val isFromNetwork: Boolean = false

)