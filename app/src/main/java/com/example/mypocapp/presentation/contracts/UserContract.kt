package com.example.mypocapp.presentation.contracts

import com.example.mypocapp.domain.mappers.UserDataModel

class UserContract {

    data class State(
        val users: List<UserDataModel> = listOf(),
        val isLoading: Boolean = false
    )
}