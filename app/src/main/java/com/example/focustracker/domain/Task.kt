package com.example.focustracker.domain

import java.time.LocalDateTime

data class Task(
    val id: Int,
    val name: String,
    val isDone: Boolean,
    val timeCreation: Long,
    val completedTime: Long?
)
