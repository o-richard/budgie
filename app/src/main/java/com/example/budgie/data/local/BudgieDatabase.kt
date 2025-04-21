package com.example.budgie.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.budgie.data.local.dao.BudgetDao
import com.example.budgie.data.local.dao.ExpenseDao
import com.example.budgie.data.local.dao.RecurringBillDao
import com.example.budgie.data.local.entity.CategoryBudget
import com.example.budgie.data.local.entity.Expense
import com.example.budgie.data.local.entity.RecurringBill

@Database(
    entities = [Expense::class, CategoryBudget::class, RecurringBill::class],
    version = 4,
    exportSchema = false,
)
abstract class BudgieDatabase : RoomDatabase() {
    abstract fun expenseDao(): ExpenseDao
    abstract fun budgetDao(): BudgetDao
    abstract fun recurringBillDao(): RecurringBillDao
}
