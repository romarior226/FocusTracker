package com.example.focustracker.data.network

data class TodoDto(
    val userId: Int,
    val id: Int,
    val title: String,
    val completed: Boolean,
    val isFromNetwork: Boolean = true
)