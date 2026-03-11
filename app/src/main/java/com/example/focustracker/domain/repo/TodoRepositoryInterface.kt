package com.example.focustracker.domain.repo

import com.example.focustracker.domain.entity.Todo


interface TodoRepositoryInterface {
    suspend fun getTodos(): List<Todo>
}