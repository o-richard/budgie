package com.example.budgie.data.local.dao


import androidx.room.*
import com.example.budgie.data.local.entity.RecurringBill
import kotlinx.coroutines.flow.Flow

@Dao
interface RecurringBillDao {
    @Insert
    suspend fun insertBill(bill: RecurringBill)

    @Update
    suspend fun updateBill(bill: RecurringBill)

    @Delete
    suspend fun deleteBill(bill: RecurringBill)

    @Query("SELECT * FROM recurring_bills")
    fun getAllBills(): Flow<List<RecurringBill>>

    @Query("SELECT * FROM recurring_bills WHERE id = :id")
    suspend fun getBillById(id: Long): RecurringBill?
}
