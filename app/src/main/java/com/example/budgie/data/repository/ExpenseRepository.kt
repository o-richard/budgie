package com.example.budgie.data.repository

import com.example.budgie.data.local.entity.Expense
import kotlinx.coroutines.flow.Flow

interface ExpenseRepository {
    suspend fun insert(expense: Expense)
    suspend fun delete(expense: Expense)
    fun getAllExpenses(): Flow<List<Expense>>
    fun getExpensesBetween(startDate: Long, endDate: Long): Flow<List<Expense>>
}
