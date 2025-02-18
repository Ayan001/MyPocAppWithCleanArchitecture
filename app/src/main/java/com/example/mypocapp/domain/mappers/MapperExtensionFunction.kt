package com.example.mypocapp.domain.mappers

import com.example.mypocapp.data.models.userdata.UserResponse

fun UserResponse.mapUsersDataItems(): UserDataModel {
    return UserDataModel(
        first_name = this.data[0].firstName,
        last_name = this.data[0].lastName,
        avatar = this.data[0].avatar,
        email = this.data[0].email
    )
}