package com.nnzapp.myqr.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.items
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.ChipDefaults
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.PositionIndicator
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.Text
import com.nnzapp.myqr.data.Bank
import com.nnzapp.myqr.presentation.viewmodel.BankListViewModel

@Composable
fun BankListScreen(
    viewModel: BankListViewModel = hiltViewModel(),
    onBankClick: (Bank) -> Unit,
    onAddBankClick: () -> Unit,
) {
    val banks by viewModel.banks.collectAsState()
    val listState = rememberScalingLazyListState()
    var bankToDelete by remember { mutableStateOf<Bank?>(null) }

    Scaffold(
        positionIndicator = {
            PositionIndicator(scalingLazyListState = listState)
        },
    ) {
        ScalingLazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = listState,
            contentPadding =
                PaddingValues(
                    top = 30.dp,
                    bottom = 30.dp,
                    start = 10.dp,
                    end = 10.dp,
                ),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            item {
                Text(
                    text = "Select Bank",
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.title3,
                    fontWeight = FontWeight.Bold,
                )
            }

            item {
                Chip(
                    onClick = onAddBankClick,
                    label = {
                        Text(
                            text = "+ Add Bank",
                            style = MaterialTheme.typography.button,
                            textAlign = TextAlign.Center,
                        )
                    },
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                    colors = ChipDefaults.primaryChipColors(),
                )
            }

            items(banks) { bank ->
                BankListItem(
                    bank = bank,
                    onClick = { onBankClick(bank) },
                    onLongClick = {
                        if (bank.id >= 1000) {
                            bankToDelete = bank
                        }
                    },
                )
            }
        }
        bankToDelete?.let { bank ->
            DeleteBankDialog(
                bankName = bank.name,
                onConfirm = {
                    viewModel.deleteBank(bank.id)
                    bankToDelete = null
                },
                onDismiss = { bankToDelete = null },
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BankListItem(
    bank: Bank,
    onClick: () -> Unit,
    onLongClick: () -> Unit = {},
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .combinedClickable(
                    onClick = onClick,
                    onLongClick = onLongClick,
                ).padding(vertical = 8.dp, horizontal = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier =
                Modifier
                    .size(32.dp)
                    .background(
                        color = Color(android.graphics.Color.parseColor("#${bank.logoColor}")),
                        shape = CircleShape,
                    ),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = bank.name.take(1),
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = bank.name,
            style = MaterialTheme.typography.body1,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f),
        )
    }
}

@Composable
fun DeleteBankDialog(
    bankName: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
) {
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
                .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "Delete Bank?",
            style = MaterialTheme.typography.title3,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = bankName,
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Chip(
                onClick = onDismiss,
                label = {
                    Text(
                        text = "Cancel",
                        textAlign = TextAlign.Center,
                    )
                },
                modifier = Modifier.weight(1f),
                colors = ChipDefaults.secondaryChipColors(),
            )

            Chip(
                onClick = onConfirm,
                label = {
                    Text(
                        text = "Delete",
                        textAlign = TextAlign.Center,
                    )
                },
                modifier = Modifier.weight(1f),
                colors = ChipDefaults.primaryChipColors(),
            )
        }
    }
}
