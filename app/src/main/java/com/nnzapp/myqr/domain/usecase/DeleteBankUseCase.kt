package com.nnzapp.myqr.domain.usecase

import com.nnzapp.myqr.domain.repository.BankRepository

class DeleteBankUseCase(private val repository: BankRepository) {
    suspend operator fun invoke(bankId: Int) {
        repository.deleteCustomBank(bankId)
    }
}
