package com.nnzapp.myqr.domain.usecase

import com.nnzapp.myqr.domain.repository.BankRepository

class GetNextBankIdUseCase(
    private val repository: BankRepository,
) {
    operator fun invoke(): Int = repository.getNextAvailableId()
}
