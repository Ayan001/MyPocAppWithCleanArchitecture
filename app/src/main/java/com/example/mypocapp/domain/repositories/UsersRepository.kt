package com.example.mypocapp.domain.repositories


import com.example.mypocapp.data.NetworkResult
import com.example.mypocapp.data.models.userdata.UserResponse
import kotlinx.coroutines.flow.Flow

interface UsersRepository {
    suspend fun fetchUsers(limit: Int = 2): Flow<NetworkResult<UserResponse>>

}