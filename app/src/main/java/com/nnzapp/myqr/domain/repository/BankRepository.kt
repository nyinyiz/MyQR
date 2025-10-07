package com.nnzapp.myqr.domain.repository

import com.nnzapp.myqr.data.Bank
import kotlinx.coroutines.flow.Flow

interface BankRepository {
    fun getAllBanks(): Flow<List<Bank>>
    suspend fun addCustomBank(bank: Bank)
    suspend fun deleteCustomBank(bankId: Int)
    suspend fun updateCustomBank(bank: Bank)
    fun getNextAvailableId(): Int
}
