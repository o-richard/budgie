package com.example.budgie.data.repository

import com.example.budgie.data.local.dao.RecurringBillDao
import com.example.budgie.data.local.entity.RecurringBill

class RecurringBillRepositoryImpl (
    private val recurringBillDao: RecurringBillDao
) : RecurringBillRepository {
    override suspend fun insertBill(bill: RecurringBill) = recurringBillDao.insertBill(bill)

    override suspend fun updateBill(bill: RecurringBill) = recurringBillDao.updateBill(bill)

    override suspend fun deleteBill(bill: RecurringBill) = recurringBillDao.deleteBill(bill)

    override fun getAllBills() = recurringBillDao.getAllBills()

    override suspend fun getBillById(id: Long) =
        recurringBillDao.getBillById(id)
}

