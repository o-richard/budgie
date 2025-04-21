package com.example.budgie.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "recurring_bills")
data class RecurringBill(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val amount: Float,
    val frequency: String,
    val dueDate: Int,
    val category: String,
    val autoMarkAsPaid: Boolean = false,
    val reminderDaysBefore: Int = 0,
    val isPaid: Boolean = false
)
