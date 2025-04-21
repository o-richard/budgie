package com.example.budgie.ui.bills

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budgie.data.local.entity.RecurringBill
import com.example.budgie.data.repository.RecurringBillRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecurringBillViewModel @Inject constructor(
    private val billsRepo: RecurringBillRepository
) : ViewModel() {
    var bills: StateFlow<List<RecurringBill>> = billsRepo
        .getAllBills()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun addBill(bill: RecurringBill) {
        viewModelScope.launch {
            billsRepo.insertBill(bill)
        }
    }

    fun deleteBill(bill: RecurringBill) {
        viewModelScope.launch {
            billsRepo.deleteBill(bill)
        }
    }
}
