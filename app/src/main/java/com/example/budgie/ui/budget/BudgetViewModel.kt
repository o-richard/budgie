package com.example.budgie.ui.budget

import com.example.budgie.data.local.entity.CategoryBudget
import com.example.budgie.data.repository.BudgetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.launch
import javax.inject.Inject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@HiltViewModel
class BudgetViewModel @Inject constructor(
    private val budgetRepo: BudgetRepository
) : ViewModel() {

    private val currentMonth = SimpleDateFormat("yyyy-MM", Locale.getDefault())
        .format(Date())

    val budgets = budgetRepo.getBudgetsForMonth(currentMonth)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun saveBudget(category: String, limit: Double) {
        viewModelScope.launch {
            budgetRepo.saveBudget(
                CategoryBudget(category = category, limit = limit, month = currentMonth)
            )
        }
    }
}

