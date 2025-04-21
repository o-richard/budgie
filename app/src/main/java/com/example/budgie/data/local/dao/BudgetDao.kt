package com.example.budgie.data.local.dao

import androidx.room.*
import com.example.budgie.data.local.entity.CategoryBudget
import kotlinx.coroutines.flow.Flow

@Dao
interface BudgetDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(budget: CategoryBudget)

    @Query("SELECT * FROM category_budgets WHERE month = :month")
    fun getBudgetsForMonth(month: String): Flow<List<CategoryBudget>>

    @Query("SELECT * FROM category_budgets WHERE category = :category AND month = :month LIMIT 1")
    suspend fun getBudget(category: String, month: String): CategoryBudget?
}
