package com.nnzapp.myqr.util

import android.graphics.Bitmap
import android.graphics.Color
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter

object QRCodeGenerator {
    fun generateQRCode(
        content: String,
        size: Int = 512,
    ): Bitmap? =
        try {
            val hints =
                hashMapOf<EncodeHintType, Int>().apply {
                    put(EncodeHintType.MARGIN, 1)
                }

            val bits =
                QRCodeWriter().encode(
                    content,
                    BarcodeFormat.QR_CODE,
                    size,
                    size,
                    hints,
                )

            Bitmap.createBitmap(size, size, Bitmap.Config.RGB_565).apply {
                for (x in 0 until size) {
                    for (y in 0 until size) {
                        setPixel(x, y, if (bits[x, y]) Color.BLACK else Color.WHITE)
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
}
