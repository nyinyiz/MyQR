package com.nnzapp.myqr.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material.*
import com.nnzapp.myqr.presentation.viewmodel.AddBankViewModel

@Composable
fun AddBankScreen(
    viewModel: AddBankViewModel = hiltViewModel(),
    onBankAdded: () -> Unit
) {
    val listState = rememberScalingLazyListState()
    val uiState by viewModel.uiState.collectAsState()

    // Navigate back when bank is saved
    LaunchedEffect(uiState.isSaved) {
        if (uiState.isSaved) {
            onBankAdded()
        }
    }

    val colors = listOf(
        "FF00A651" to "Green",
        "FF4E2A84" to "Purple",
        "FF1E4693" to "Blue",
        "FFFDB913" to "Yellow",
        "FF00AEEF" to "Cyan",
        "FFFF6600" to "Orange",
        "FFE91E63" to "Pink",
        "FF9C27B0" to "Violet",
        "FF4CAF50" to "Light Green",
        "FFF44336" to "Red"
    )

    Scaffold(
        positionIndicator = {
            PositionIndicator(scalingLazyListState = listState)
        }
    ) {
        ScalingLazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = listState,
            contentPadding = PaddingValues(
                top = 30.dp,
                bottom = 30.dp,
                start = 10.dp,
                end = 10.dp
            ),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                Text(
                    text = "Add Bank",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.title3,
                    fontWeight = FontWeight.Bold
                )
            }

            // Bank Name Input
            item {
                InputField(
                    label = "Bank Name",
                    value = uiState.bankName,
                    onValueChange = { viewModel.updateBankName(it) },
                    placeholder = "e.g., My Bank",
                    imeAction = ImeAction.Done
                )
            }

            // Account Name Input
            item {
                InputField(
                    label = "Account Name",
                    value = uiState.accountName,
                    onValueChange = { viewModel.updateAccountName(it) },
                    placeholder = "e.g., John Doe",
                    imeAction = ImeAction.Done
                )
            }

            // QR Code Data Input
            item {
                InputField(
                    label = "QR Code Data",
                    value = uiState.qrCodeData,
                    onValueChange = { viewModel.updateQrCodeData(it) },
                    placeholder = "Paste QR data here",
                    imeAction = ImeAction.Done
                )
            }

            // Color Selection
            item {
                Text(
                    text = "Select Color",
                    style = MaterialTheme.typography.caption1,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
                )
            }

            items(colors.size) { index ->
                val (colorHex, colorName) = colors[index]
                ColorSelectionItem(
                    colorHex = colorHex,
                    colorName = colorName,
                    isSelected = uiState.selectedColor == colorHex,
                    onClick = { viewModel.updateSelectedColor(colorHex) }
                )
            }

            // Save Button
            item {
                Chip(
                    onClick = {
                        viewModel.saveBank()
                    },
                    label = {
                        Text(
                            text = if (uiState.isSaving) "Saving..." else "Save Bank",
                            style = MaterialTheme.typography.button,
                            textAlign = TextAlign.Center
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    colors = ChipDefaults.primaryChipColors(),
                    enabled = viewModel.isFormValid() && !uiState.isSaving
                )
            }
        }
    }
}

@Composable
fun InputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    imeAction: ImeAction = ImeAction.Default
) {
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.caption1,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colors.surface,
                    shape = MaterialTheme.shapes.small
                )
                .padding(horizontal = 8.dp, vertical = 8.dp)
        ) {
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                textStyle = TextStyle(
                    color = MaterialTheme.colors.onSurface,
                    fontSize = 12.sp
                ),
                cursorBrush = SolidColor(MaterialTheme.colors.primary),
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(imeAction = imeAction),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                    },
                    onNext = {
                        focusManager.moveFocus(androidx.compose.ui.focus.FocusDirection.Down)
                    }
                ),
                singleLine = true,
                decorationBox = { innerTextField ->
                    if (value.isEmpty()) {
                        Text(
                            text = placeholder,
                            style = MaterialTheme.typography.caption2,
                            color = MaterialTheme.colors.onSurface.copy(alpha = 0.5f)
                        )
                    }
                    innerTextField()
                }
            )
        }
    }
}

@Composable
fun ColorSelectionItem(
    colorHex: String,
    colorName: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Chip(
        onClick = onClick,
        label = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .background(
                            color = Color(android.graphics.Color.parseColor("#$colorHex")),
                            shape = CircleShape
                        )
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = colorName,
                    style = MaterialTheme.typography.caption1
                )
            }
        },
        modifier = Modifier.fillMaxWidth(),
        colors = if (isSelected) {
            ChipDefaults.primaryChipColors()
        } else {
            ChipDefaults.secondaryChipColors()
        }
    )
}
