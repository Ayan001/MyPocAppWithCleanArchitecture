package com.example.mypocapp.domain.usecase.users

import com.example.mypocapp.data.NetworkResult
import com.example.mypocapp.domain.mappers.UserDataModel
import com.example.mypocapp.domain.mappers.mapUsersDataItems
import com.example.mypocapp.domain.repositories.UsersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetUsersUseCaseImpl(private val usersRepo: UsersRepository) : GetUsersUseCase {
    override suspend fun execute(): Flow<NetworkResult<List<UserDataModel>>> = flow {
        usersRepo.fetchUsers().collect { userResponse ->
            when (userResponse) {
                is NetworkResult.Success -> {

                    val userDataList = userResponse.data?.data?.map { user ->
                        user.mapUsersDataItems()
                    }
                    emit(NetworkResult.Success(userDataList))
                }

                is NetworkResult.Error -> {
                    emit(NetworkResult.Error(userResponse.message))
                }

                is NetworkResult.Loading -> {
                    emit(NetworkResult.Loading())
                }
            }
        }
    }
}

