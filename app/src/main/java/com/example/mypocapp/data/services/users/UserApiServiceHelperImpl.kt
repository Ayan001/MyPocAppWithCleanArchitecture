package com.example.mypocapp.data.services.users

import com.example.mypocapp.data.services.ApiService
import com.example.mypocapp.data.models.userdata.UserResponse
import retrofit2.Response

class UserApiServiceHelperImpl(private val service: ApiService) : UserApiServiceHelper {
    override suspend fun fetchAllUsers(limit: Int): Response<List<UserResponse>> =
        service.fetchUsers(limit)

}