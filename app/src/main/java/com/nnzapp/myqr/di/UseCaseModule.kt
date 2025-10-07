package com.nnzapp.myqr.di

import com.nnzapp.myqr.domain.repository.BankRepository
import com.nnzapp.myqr.domain.usecase.AddBankUseCase
import com.nnzapp.myqr.domain.usecase.DeleteBankUseCase
import com.nnzapp.myqr.domain.usecase.GetAllBanksUseCase
import com.nnzapp.myqr.domain.usecase.GetNextBankIdUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {
    @Provides
    @ViewModelScoped
    fun provideGetAllBanksUseCase(repository: BankRepository): GetAllBanksUseCase = GetAllBanksUseCase(repository)

    @Provides
    @ViewModelScoped
    fun provideAddBankUseCase(repository: BankRepository): AddBankUseCase = AddBankUseCase(repository)

    @Provides
    @ViewModelScoped
    fun provideDeleteBankUseCase(repository: BankRepository): DeleteBankUseCase = DeleteBankUseCase(repository)

    @Provides
    @ViewModelScoped
    fun provideGetNextBankIdUseCase(repository: BankRepository): GetNextBankIdUseCase = GetNextBankIdUseCase(repository)
}
