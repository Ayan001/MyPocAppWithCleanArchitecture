package com.example.mypocapp.data.repositories

import com.example.mypocapp.data.NetworkResult
import com.example.mypocapp.data.models.userdata.UserResponse
import com.example.mypocapp.data.services.users.UserApiServiceHelper
import com.example.mypocapp.domain.repositories.UsersRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class UsersRepositoryImpl(

    private val usersApiService: UserApiServiceHelper) : UsersRepository {

    override suspend fun fetchUsers(limit: Int) = flow<NetworkResult<List<UserResponse>>> {
        emit(NetworkResult.Loading())
        with(usersApiService.fetchAllUsers(limit)) {
            if (isSuccessful) {
                emit(NetworkResult.Success(this.body()))
            } else {
                emit(NetworkResult.Error(this.errorBody().toString()))
            }
        }
    }.catch {
        emit(NetworkResult.Error(it.localizedMessage))
    }

}