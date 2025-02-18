package com.example.mypocapp.di

import com.example.mypocapp.data.services.ApiService
import com.example.mypocapp.data.services.users.UserApiServiceHelper
import com.example.mypocapp.data.services.users.UserApiServiceHelperImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object ServiceHelperModule {

    @Provides
    fun provideUserApiServiceHelper(apiService: ApiService): UserApiServiceHelper {
        return UserApiServiceHelperImpl(apiService)
    }

}