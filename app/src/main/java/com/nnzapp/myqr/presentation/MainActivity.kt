package com.nnzapp.myqr.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.nnzapp.myqr.presentation.theme.MyQRTheme
import com.nnzapp.myqr.presentation.viewmodel.AddBankViewModel
import com.nnzapp.myqr.presentation.viewmodel.BankListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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
        val navController = rememberSwipeDismissableNavController()
        val bankListViewModel: BankListViewModel = hiltViewModel()
        val banks by bankListViewModel.banks.collectAsState()

        SwipeDismissableNavHost(
            navController = navController,
            startDestination = "bank_list",
        ) {
            composable("bank_list") {
                BankListScreen(
                    viewModel = bankListViewModel,
                    onBankClick = { bank ->
                        navController.navigate("qr_code/${bank.id}")
                    },
                    onAddBankClick = {
                        navController.navigate("add_bank")
                    },
                )
            }

            composable("qr_code/{bankId}") { backStackEntry ->
                val bankId = backStackEntry.arguments?.getString("bankId")?.toIntOrNull()
                val bank = banks.find { it.id == bankId }

                if (bank != null) {
                    QRCodeScreen(bank = bank)
                }
            }

            composable("add_bank") {
                val addBankViewModel: AddBankViewModel = hiltViewModel()
                AddBankScreen(
                    viewModel = addBankViewModel,
                    onBankAdded = {
                        navController.popBackStack()
                    },
                )
            }
        }
    }
}
