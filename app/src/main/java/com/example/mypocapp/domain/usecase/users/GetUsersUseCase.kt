package com.example.mypocapp.domain.usecase.users

import com.example.mypocapp.data.NetworkResult
import com.example.mypocapp.domain.mappers.UserDataModel
import kotlinx.coroutines.flow.Flow

interface GetUsersUseCase {
    suspend fun execute(): Flow<NetworkResult<List<UserDataModel>>>
}