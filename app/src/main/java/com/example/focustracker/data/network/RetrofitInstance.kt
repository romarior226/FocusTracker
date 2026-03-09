package com.example.focustracker.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

    object RetrofitInstance {
    private const val BASE_URL = "https://jsonplaceholder.typicode.com/ "

    val api: TodoApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TodoApi::class.java)
    }
}