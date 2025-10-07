package com.nnzapp.myqr.data

import android.content.Context
import kotlinx.serialization.json.Json
import java.io.BufferedReader
import java.io.InputStreamReader

object BankRepository {

    private val json = Json { ignoreUnknownKeys = true }

    fun loadBanks(context: Context): List<Bank> {
        return try {
            val inputStream = context.resources.openRawResource(
                context.resources.getIdentifier("banks", "raw", context.packageName)
            )
            val reader = BufferedReader(InputStreamReader(inputStream))
            val jsonString = reader.use { it.readText() }
            json.decodeFromString<List<Bank>>(jsonString)
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}
