package com.example.budgie.ui.expense

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budgie.data.local.entity.Expense
import com.example.budgie.data.repository.ExpenseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExpenseViewModel @Inject constructor(
    private val repository: ExpenseRepository
) : ViewModel() {

    fun addExpense(
        amount: Double,
        category: String,
        note: String?,
        tags: String?
    ) {
        val expense = Expense(
            amount = amount,
            category = category,
            date = System.currentTimeMillis(),
            note = note,
            tags = tags
        )
        viewModelScope.launch {
            repository.insert(expense)
        }
    }
}
