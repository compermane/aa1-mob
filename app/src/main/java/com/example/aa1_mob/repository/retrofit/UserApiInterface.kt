package com.example.aa1_mob.repository.retrofit

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


interface UserApiInterface {

    @POST("login")
    suspend fun login(@Body loginRequest: LoginRequest) : Response<UserData>

    @POST("register")
    suspend fun register(@Body registerRequest: RegisterRequest) : Response<UserData>

}