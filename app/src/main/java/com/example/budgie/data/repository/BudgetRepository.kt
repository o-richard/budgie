package com.example.budgie.data.repository

import com.example.budgie.data.local.entity.CategoryBudget
import kotlinx.coroutines.flow.Flow

interface BudgetRepository {
    suspend fun saveBudget(budget: CategoryBudget)
    fun getBudgetsForMonth(month: String): Flow<List<CategoryBudget>>
    suspend fun getBudgetForCategory(category: String, month: String): CategoryBudget?
}
