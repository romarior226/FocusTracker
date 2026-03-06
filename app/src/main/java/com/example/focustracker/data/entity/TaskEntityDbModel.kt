package com.example.focustracker.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime


@Entity(tableName = "tasks")
data class TaskEntityDbModel(
    @PrimaryKey val id: Int,
    val name: String,
    val isDone: Boolean,
    val timeCreation: Long,
    val completedTime: Long?

)