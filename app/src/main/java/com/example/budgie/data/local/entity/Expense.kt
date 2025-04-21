package com.example.budgie.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expenses")
data class Expense(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val amount: Double,
    val category: String,
    val date: Long,
    val note: String? = null,
    val tags: String? = null
)
