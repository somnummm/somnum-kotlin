package com.example.somnum.di

import com.example.somnum.data.remote.SupabaseClient
import com.example.somnum.data.repository.UserRepository
import com.example.somnum.data.repository.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun bindSupabaseClient(
        supabaseClient: SupabaseClient
    ): SupabaseClient

    @Binds
    @Singleton
    abstract fun bindUserRepository(
        userRepositoryImpl: UserRepositoryImpl
    ): UserRepository
}