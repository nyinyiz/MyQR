package com.nnzapp.myqr.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import com.nnzapp.myqr.data.Bank
import com.nnzapp.myqr.util.QRCodeGenerator

@Composable
fun QRCodeScreen(bank: Bank) {
    val bankColor = Color(android.graphics.Color.parseColor("#${bank.logoColor}"))

    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors =
                            listOf(
                                MaterialTheme.colors.background,
                                bankColor.copy(alpha = 0.1f),
                            ),
                    ),
                ),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(8.dp),
        ) {
            Text(
                text = bank.name,
                style = MaterialTheme.typography.title2,
                fontWeight = FontWeight.Bold,
                color = bankColor,
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.height(8.dp))

            // QR Code with decorative border
            Box(
                modifier =
                    Modifier
                        .fillMaxWidth(0.75f)
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.White)
                        .border(
                            width = 3.dp,
                            brush =
                                Brush.linearGradient(
                                    colors =
                                        listOf(
                                            bankColor.copy(alpha = 0.6f),
                                            bankColor.copy(alpha = 0.3f),
                                        ),
                                ),
                            shape = RoundedCornerShape(12.dp),
                        ).padding(8.dp),
                contentAlignment = Alignment.Center,
            ) {
                val qrBitmap = QRCodeGenerator.generateQRCode(bank.qrCodeData, 512)
                if (qrBitmap != null) {
                    Image(
                        bitmap = qrBitmap.asImageBitmap(),
                        contentDescription = "QR Code for ${bank.name}",
                        modifier =
                            Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(4.dp)),
                    )
                } else {
                    Text(
                        text = "QR Error",
                        fontSize = 10.sp,
                        color = Color.DarkGray,
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Scan to Pay",
                style = MaterialTheme.typography.caption1,
                fontWeight = FontWeight.Medium,
                color = bankColor.copy(alpha = 0.8f),
                textAlign = TextAlign.Center,
            )
        }
    }
}
