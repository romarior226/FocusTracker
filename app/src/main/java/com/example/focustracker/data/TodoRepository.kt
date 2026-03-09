package com.example.focustracker.data

import com.example.focustracker.data.network.RetrofitInstance
import com.example.focustracker.data.network.TodoDto

class TodoRepository {
    suspend fun getTodos(): List<TodoDto> {
        return RetrofitInstance.api.getTodos(10)
    }

}