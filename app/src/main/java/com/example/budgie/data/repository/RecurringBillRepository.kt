package com.example.budgie.data.repository

import com.example.budgie.data.local.entity.RecurringBill
import kotlinx.coroutines.flow.Flow

interface RecurringBillRepository {
    suspend fun insertBill(bill: RecurringBill)
    suspend fun updateBill(bill: RecurringBill)
    suspend fun deleteBill(bill: RecurringBill)
    fun getAllBills(): Flow<List<RecurringBill>>
    suspend fun getBillById(id: Long): RecurringBill?
}
