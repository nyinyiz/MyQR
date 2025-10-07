package com.nnzapp.myqr.domain.usecase

import com.nnzapp.myqr.data.Bank
import com.nnzapp.myqr.domain.repository.BankRepository
import kotlinx.coroutines.flow.Flow

class GetAllBanksUseCase(
    private val repository: BankRepository,
) {
    operator fun invoke(): Flow<List<Bank>> = repository.getAllBanks()
}
