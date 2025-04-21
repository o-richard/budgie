package com.example.budgie.data.repository

import com.example.budgie.data.local.dao.BudgetDao
import com.example.budgie.data.local.entity.CategoryBudget

class BudgetRepositoryImpl (
    private val budgetDao: BudgetDao
) : BudgetRepository {
    override suspend fun saveBudget(budget: CategoryBudget) = budgetDao.upsert(budget)

    override fun getBudgetsForMonth(month: String) = budgetDao.getBudgetsForMonth(month)

    override suspend fun getBudgetForCategory(category: String, month: String) =
        budgetDao.getBudget(category, month)
}
