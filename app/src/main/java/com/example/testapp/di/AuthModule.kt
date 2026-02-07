package com.example.testapp.di

import com.example.testapp.data.auth.AuthRepositoryImpl
import com.example.testapp.data.auth.EmailValidatorImpl
import com.example.testapp.domain.auth.AuthRepository
import com.example.testapp.domain.auth.EmailValidator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthModule {
    @Binds
    @Singleton
    abstract fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    abstract fun bindEmailValidator(impl: EmailValidatorImpl): EmailValidator
}
