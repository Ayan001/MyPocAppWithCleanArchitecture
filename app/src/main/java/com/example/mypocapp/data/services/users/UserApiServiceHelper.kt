package com.example.mypocapp.data.services.users

import com.example.mypocapp.data.models.userdata.UserResponse
import retrofit2.Response

interface UserApiServiceHelper {
    suspend fun fetchAllUsers(limit: Int): Response<List<UserResponse>>
}