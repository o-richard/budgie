package com.example.budgie.data.repository

import com.example.budgie.data.local.dao.ExpenseDao
import com.example.budgie.data.local.entity.Expense
import kotlinx.coroutines.flow.Flow

class ExpenseRepositoryImpl(
    private val dao: ExpenseDao
) : ExpenseRepository {

    override suspend fun insert(expense: Expense) = dao.insertExpense(expense)

    override suspend fun delete(expense: Expense) = dao.deleteExpense(expense)

    override fun getAllExpenses(): Flow<List<Expense>> = dao.getAllExpenses()

    override fun getExpensesBetween(startDate: Long, endDate: Long): Flow<List<Expense>> =
        dao.getExpensesBetween(startDate, endDate)
}
