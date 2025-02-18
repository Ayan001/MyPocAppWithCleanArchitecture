package com.example.mypocapp.di

import com.example.mypocapp.data.repositories.UsersRepositoryImpl
import com.example.mypocapp.data.services.users.UserApiServiceHelper
import com.example.mypocapp.domain.repositories.UsersRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object RepoModule {
    @Provides
    fun provideUserRepository(
        catsApiService: UserApiServiceHelper, ): UsersRepository {

        return UsersRepositoryImpl(catsApiService)
    }
}