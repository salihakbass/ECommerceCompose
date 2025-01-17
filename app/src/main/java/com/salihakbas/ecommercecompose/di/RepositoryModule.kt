package com.salihakbas.ecommercecompose.di

import com.salihakbas.ecommercecompose.data.repository.FirebaseAuthRepositoryImpl
import com.salihakbas.ecommercecompose.data.repository.MainRepositoryImpl
import com.salihakbas.ecommercecompose.domain.repository.FirebaseAuthRepository
import com.salihakbas.ecommercecompose.domain.repository.MainRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindMainRepository(repositoryImpl: MainRepositoryImpl): MainRepository

    @Binds
    abstract fun bindFirebaseAuthRepository(repositoryImpl: FirebaseAuthRepositoryImpl): FirebaseAuthRepository

}