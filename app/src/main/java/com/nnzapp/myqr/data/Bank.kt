package com.nnzapp.myqr.data

import kotlinx.serialization.Serializable

@Serializable
data class Bank(
    val id: Int,
    val name: String,
    val accountName: String, // Account holder name
    val logoColor: String, // Color in hex format (e.g., "FF00A651")
    val qrCodeData: String, // The actual QR code data (PromptPay format)
)
