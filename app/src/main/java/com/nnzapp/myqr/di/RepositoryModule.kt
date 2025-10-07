package com.nnzapp.myqr.di

import android.content.Context
import com.nnzapp.myqr.data.BankRepositoryImpl
import com.nnzapp.myqr.domain.repository.BankRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideBankRepository(
        @ApplicationContext context: Context
    ): BankRepository {
        return BankRepositoryImpl(context)
    }
}
