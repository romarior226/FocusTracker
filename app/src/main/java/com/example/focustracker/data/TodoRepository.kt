package com.example.focustracker.data

import com.example.focustracker.data.mapper.toTodo
import com.example.focustracker.data.network.RetrofitInstance
import com.example.focustracker.domain.entity.Todo
import com.example.focustracker.domain.repo.TodoRepositoryInterface
import javax.inject.Inject

class TodoRepository @Inject constructor() : TodoRepositoryInterface {
    override suspend fun getTodos(): List<Todo> {
        return RetrofitInstance.api.getTodos(10).map {
            it.toTodo()
        }
    }
}
