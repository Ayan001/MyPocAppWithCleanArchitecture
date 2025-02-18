package com.example.mypocapp.data.services

import com.example.mypocapp.data.models.userdata.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {


    @GET("api/users")
    suspend fun fetchUsers(
        @Query("page") limit: Int
    ): Response<UserResponse>



}