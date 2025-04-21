package com.example.budgie.di

import android.app.Application
import androidx.room.Room
import com.example.budgie.data.local.BudgieDatabase
import com.example.budgie.data.local.dao.BudgetDao
import com.example.budgie.data.local.dao.ExpenseDao
import com.example.budgie.data.local.dao.RecurringBillDao
import com.example.budgie.data.repository.BudgetRepository
import com.example.budgie.data.repository.BudgetRepositoryImpl
import com.example.budgie.data.repository.ExpenseRepository
import com.example.budgie.data.repository.ExpenseRepositoryImpl
import com.example.budgie.data.repository.RecurringBillRepository
import com.example.budgie.data.repository.RecurringBillRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application): BudgieDatabase =
        Room.databaseBuilder(app, BudgieDatabase::class.java, "budgie_db")
            .fallbackToDestructiveMigration(true)
            .build()

    @Provides
    fun provideExpenseDao(db: BudgieDatabase): ExpenseDao = db.expenseDao()

    @Provides
    fun provideExpenseRepository(dao: ExpenseDao): ExpenseRepository =
        ExpenseRepositoryImpl(dao)

    @Provides
    fun provideBudgetDao(db: BudgieDatabase): BudgetDao = db.budgetDao()

    @Provides
    fun provideBudgetRepository(dao: BudgetDao): BudgetRepository =
        BudgetRepositoryImpl(dao)

    @Provides
    fun provideRecurringBillDao(db: BudgieDatabase): RecurringBillDao = db.recurringBillDao()

    @Provides
    fun provideRecurringBillRepository(dao: RecurringBillDao): RecurringBillRepository =
        RecurringBillRepositoryImpl(dao)
}
