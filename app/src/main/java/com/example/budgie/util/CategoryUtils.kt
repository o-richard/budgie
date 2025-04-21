package com.example.budgie.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.Color

val categoryColors = mapOf(
    "Food" to Color(0xFFFFB300),
    "Transport" to Color(0xFF64B5F6),
    "Subscriptions" to Color(0xFFBA68C8),
    "Shopping" to Color(0xFFE57373),
    "Other" to Color.Gray
)

val categoryIcons = mapOf(
    "Food" to Icons.Default.Restaurant,
    "Transport" to Icons.Default.DirectionsCar,
    "Subscriptions" to Icons.Default.Receipt,
    "Shopping" to Icons.Default.ShoppingCart,
    "Other" to Icons.Default.AttachMoney
)
