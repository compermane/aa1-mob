package com.example.aa1_mob.repository.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.getValue

object RetrofitInstance {
    private
    const val BASE_URL = "http://10.0.2.2:3001/"
    const val TO_TEST_BASE_URL = "http://localhost:3001/"

    val api : UserApiInterface by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(UserApiInterface::class.java)
    }

    val testapi : UserApiInterface by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(TO_TEST_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(UserApiInterface::class.java)
    }
}