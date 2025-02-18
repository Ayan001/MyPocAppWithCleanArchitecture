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


    @GET("v1/images/search?size=small&has_breeds=true&order=RANDOM&page=0")
    suspend fun fetchUsers(
        @Query("limit") limit: Int
    ): Response<List<UserResponse>>



}