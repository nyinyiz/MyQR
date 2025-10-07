package com.nnzapp.myqr.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nnzapp.myqr.data.Bank
import com.nnzapp.myqr.domain.usecase.AddBankUseCase
import com.nnzapp.myqr.domain.usecase.GetNextBankIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AddBankUiState(
    val bankName: String = "",
    val accountName: String = "",
    val qrCodeData: String = "",
    val selectedColor: String = "FF00A651",
    val isSaving: Boolean = false,
    val isSaved: Boolean = false
)

@HiltViewModel
class AddBankViewModel @Inject constructor(
    private val addBankUseCase: AddBankUseCase,
    private val getNextBankIdUseCase: GetNextBankIdUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddBankUiState())
    val uiState: StateFlow<AddBankUiState> = _uiState.asStateFlow()

    fun updateBankName(name: String) {
        _uiState.value = _uiState.value.copy(bankName = name)
    }

    fun updateAccountName(name: String) {
        _uiState.value = _uiState.value.copy(accountName = name)
    }

    fun updateQrCodeData(data: String) {
        _uiState.value = _uiState.value.copy(qrCodeData = data)
    }

    fun updateSelectedColor(color: String) {
        _uiState.value = _uiState.value.copy(selectedColor = color)
    }

    fun saveBank() {
        val state = _uiState.value
        if (state.bankName.isBlank() || state.accountName.isBlank() || state.qrCodeData.isBlank()) {
            return
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isSaving = true)
            try {
                val newBank = Bank(
                    id = getNextBankIdUseCase(),
                    name = state.bankName,
                    accountName = state.accountName,
                    logoColor = state.selectedColor,
                    qrCodeData = state.qrCodeData
                )
                addBankUseCase(newBank)
                _uiState.value = _uiState.value.copy(isSaving = false, isSaved = true)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isSaving = false)
            }
        }
    }

    fun isFormValid(): Boolean {
        val state = _uiState.value
        return state.bankName.isNotBlank() &&
               state.accountName.isNotBlank() &&
               state.qrCodeData.isNotBlank()
    }
}
