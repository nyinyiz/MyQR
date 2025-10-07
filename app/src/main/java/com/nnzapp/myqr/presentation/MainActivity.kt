package com.nnzapp.myqr.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.nnzapp.myqr.data.BankRepository
import com.nnzapp.myqr.presentation.theme.MyQRTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        setTheme(android.R.style.Theme_DeviceDefault)

        setContent {
            WearApp()
        }
    }
}

@Composable
fun WearApp() {
    MyQRTheme {
        val context = LocalContext.current
        val navController = rememberSwipeDismissableNavController()
        val banks = BankRepository.loadBanks(context)

        SwipeDismissableNavHost(
            navController = navController,
            startDestination = "bank_list"
        ) {
            composable("bank_list") {
                BankListScreen(
                    onBankClick = { bank ->
                        navController.navigate("qr_code/${bank.id}")
                    }
                )
            }

            composable("qr_code/{bankId}") { backStackEntry ->
                val bankId = backStackEntry.arguments?.getString("bankId")?.toIntOrNull()
                val bank = banks.find { it.id == bankId }

                if (bank != null) {
                    QRCodeScreen(bank = bank)
                }
            }
        }
    }
}