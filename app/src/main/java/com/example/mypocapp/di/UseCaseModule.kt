package com.example.mypocapp.di

import com.example.mypocapp.domain.repositories.UsersRepository
import com.example.mypocapp.domain.usecase.users.GetUsersUseCase
import com.example.mypocapp.domain.usecase.users.GetUsersUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {
    @Provides
    fun provideUserUseCase(catsRepo: UsersRepository): GetUsersUseCase {
        return GetUsersUseCaseImpl(catsRepo)
    }
}