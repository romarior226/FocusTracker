package com.example.focustracker.domain.entity

data class Task(
    val id: Int,
    val name: String,
    val isDone: Boolean,
    val timeCreation: Long,
    val completedTime: Long?,
    val isFromNetwork: Boolean = false
)
