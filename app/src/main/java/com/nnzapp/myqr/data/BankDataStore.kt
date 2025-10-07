package com.nnzapp.myqr.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "banks")

class BankDataStore(private val context: Context) {

    private val json = Json { ignoreUnknownKeys = true }

    companion object {
        private val CUSTOM_BANKS_KEY = stringPreferencesKey("custom_banks")
    }

    val customBanks: Flow<List<Bank>> = context.dataStore.data.map { preferences ->
        val banksJson = preferences[CUSTOM_BANKS_KEY] ?: "[]"
        try {
            json.decodeFromString<List<Bank>>(banksJson)
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    suspend fun addBank(bank: Bank) {
        context.dataStore.edit { preferences ->
            val currentBanks = try {
                val banksJson = preferences[CUSTOM_BANKS_KEY] ?: "[]"
                json.decodeFromString<List<Bank>>(banksJson).toMutableList()
            } catch (e: Exception) {
                mutableListOf()
            }

            currentBanks.add(bank)
            preferences[CUSTOM_BANKS_KEY] = json.encodeToString(currentBanks)
        }
    }

    suspend fun deleteBank(bankId: Int) {
        context.dataStore.edit { preferences ->
            val currentBanks = try {
                val banksJson = preferences[CUSTOM_BANKS_KEY] ?: "[]"
                json.decodeFromString<List<Bank>>(banksJson).toMutableList()
            } catch (e: Exception) {
                mutableListOf()
            }

            currentBanks.removeAll { it.id == bankId }
            preferences[CUSTOM_BANKS_KEY] = json.encodeToString(currentBanks)
        }
    }

    suspend fun updateBank(bank: Bank) {
        context.dataStore.edit { preferences ->
            val currentBanks = try {
                val banksJson = preferences[CUSTOM_BANKS_KEY] ?: "[]"
                json.decodeFromString<List<Bank>>(banksJson).toMutableList()
            } catch (e: Exception) {
                mutableListOf()
            }

            val index = currentBanks.indexOfFirst { it.id == bank.id }
            if (index != -1) {
                currentBanks[index] = bank
                preferences[CUSTOM_BANKS_KEY] = json.encodeToString(currentBanks)
            }
        }
    }
}
