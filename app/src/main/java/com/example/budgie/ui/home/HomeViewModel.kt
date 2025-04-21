package com.example.budgie.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budgie.data.local.entity.CategoryBudget
import com.example.budgie.data.local.entity.Expense
import com.example.budgie.data.repository.BudgetRepository
import com.example.budgie.data.repository.ExpenseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: ExpenseRepository,
    private val budgetRepository: BudgetRepository
) : ViewModel() {

    val expenses: StateFlow<List<Expense>> = repository
        .getAllExpenses()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val startOfMonth = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay().toInstant(
        ZoneOffset.UTC).toEpochMilli()
    private val endOfMonth = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()).atTime(23, 59, 59, 999_999_999).toInstant(ZoneOffset.UTC).toEpochMilli()
    val currentMonthExpenses: StateFlow<List<Expense>> = repository
        .getExpensesBetween(startOfMonth, endOfMonth)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val currentMonthBudget: StateFlow<List<CategoryBudget>> = budgetRepository
        .getBudgetsForMonth(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM")))
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    data class GroupedExpenses(
        val dateLabel: String,
        val expenses: List<Expense>
    )

    private fun groupByDay(expenses: List<Expense>): List<GroupedExpenses> {
        val today = getStartOfDay(System.currentTimeMillis())
        val yesterday = getStartOfDay(System.currentTimeMillis() - 86400000L)

        return expenses
            .groupBy {
                val day = getStartOfDay(it.date)
                when (day) {
                    today -> "Today"
                    yesterday -> "Yesterday"
                    else -> SimpleDateFormat("MMM dd", Locale.getDefault()).format(Date(it.date))
                }
            }
            .map { (label, expenses) -> GroupedExpenses(label, expenses) }
    }

    private fun getStartOfDay(time: Long): Long {
        val cal = Calendar.getInstance().apply {
            timeInMillis = time
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        return cal.timeInMillis
    }

    private val _selectedCategory = MutableStateFlow("All")
    val selectedCategory = _selectedCategory.asStateFlow()

    fun selectCategory(category: String) {
        _selectedCategory.value = category
    }

    val groupedExpenses: StateFlow<List<GroupedExpenses>> = combine(
        expenses, selectedCategory
    ) { list, category ->
        val filtered = if (category == "All") list else list.filter { it.category == category }
        groupByDay(filtered)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
}
