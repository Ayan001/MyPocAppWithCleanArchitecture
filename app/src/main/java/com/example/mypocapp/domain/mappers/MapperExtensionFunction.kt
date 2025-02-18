package com.example.mypocapp.domain.mappers

import com.example.mypocapp.data.models.userdata.Data
import com.example.mypocapp.data.models.userdata.UserResponse

fun Data.mapUsersDataItems(): UserDataModel {
    return UserDataModel(
        first_name = firstName,
        last_name = lastName,
        avatar = avatar,
        email = email
    )
}