package com.nnzapp.myqr.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nnzapp.myqr.data.Bank
import com.nnzapp.myqr.domain.usecase.DeleteBankUseCase
import com.nnzapp.myqr.domain.usecase.GetAllBanksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BankListViewModel @Inject constructor(
    private val getAllBanksUseCase: GetAllBanksUseCase,
    private val deleteBankUseCase: DeleteBankUseCase
) : ViewModel() {

    val banks: StateFlow<List<Bank>> = getAllBanksUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun deleteBank(bankId: Int) {
        viewModelScope.launch {
            deleteBankUseCase(bankId)
        }
    }
}
