package com.nnzapp.myqr.domain.usecase

import com.nnzapp.myqr.data.Bank
import com.nnzapp.myqr.domain.repository.BankRepository

class AddBankUseCase(
    private val repository: BankRepository,
) {
    suspend operator fun invoke(bank: Bank) {
        repository.addCustomBank(bank)
    }
}
