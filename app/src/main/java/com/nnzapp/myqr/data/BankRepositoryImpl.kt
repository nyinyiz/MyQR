package com.nnzapp.myqr.data

import android.content.Context
import com.nnzapp.myqr.domain.repository.BankRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.serialization.json.Json
import java.io.BufferedReader
import java.io.InputStreamReader

class BankRepositoryImpl(
    private val context: Context,
) : BankRepository {
    private val json = Json { ignoreUnknownKeys = true }
    private val dataStore = BankDataStore(context)

    companion object {
        private const val CUSTOM_BANK_ID_START = 1000
    }

    private fun loadDefaultBanks(): List<Bank> =
        try {
            val inputStream =
                context.resources.openRawResource(
                    context.resources.getIdentifier("banks", "raw", context.packageName),
                )
            val reader = BufferedReader(InputStreamReader(inputStream))
            val jsonString = reader.use { it.readText() }
            json.decodeFromString<List<Bank>>(jsonString)
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }

    override fun getAllBanks(): Flow<List<Bank>> {
        val defaultBanks = flowOf(loadDefaultBanks())
        return combine(defaultBanks, dataStore.customBanks) { defaults, customs ->
            defaults + customs
        }
    }

    override suspend fun addCustomBank(bank: Bank) {
        dataStore.addBank(bank)
    }

    override suspend fun deleteCustomBank(bankId: Int) {
        dataStore.deleteBank(bankId)
    }

    override suspend fun updateCustomBank(bank: Bank) {
        dataStore.updateBank(bank)
    }

    override fun getNextAvailableId(): Int {
        val defaultBanks = loadDefaultBanks()
        val maxDefaultId = defaultBanks.maxOfOrNull { it.id } ?: 0
        return maxOf(CUSTOM_BANK_ID_START, maxDefaultId + 1)
    }
}
