package com.example.focustracker.data.network

import retrofit2.http.GET
import retrofit2.http.Query

interface TodoApi {

    @GET("todos")
    suspend fun getTodos(   @Query("_limit") limit: Int): List<TodoDto>
}