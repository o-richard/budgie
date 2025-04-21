package com.example.budgie.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Money
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.budgie.ui.budget.BudgetViewModel
import com.example.budgie.util.categoryColors
import com.example.budgie.util.categoryIcons


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetSetupScreen(
    navController: NavController,
    viewModel: BudgetViewModel = hiltViewModel(),
) {
    val budgets by viewModel.budgets.collectAsState()
    val categories = listOf("Food", "Transport", "Subscriptions", "Shopping", "Other")

    val budgetMap = budgets.associateBy { it.category }
    val inputs = remember { mutableStateMapOf<String, String>() }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Set Monthly Budgets") })
        },
        content = { padding ->
            Column(Modifier.padding(padding).padding(16.dp)) {
                categories.forEach { category ->
                    val current = budgetMap[category]?.limit?.toString() ?: ""
                    val input = inputs.getOrPut(category) { current }

                    OutlinedTextField(
                        value = input,
                        onValueChange = { inputs[category] = it },
                        label = { Text(category) },
                        leadingIcon = {
                            Icon(categoryIcons[category] ?: Icons.Default.Money, null, tint = categoryColors[category] ?: Color.Gray)
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                    )
                }

                Spacer(Modifier.height(24.dp))
                Button(
                    onClick = {
                        inputs.forEach { (category, value) ->
                            value.toDoubleOrNull()?.let { viewModel.saveBudget(category, it) }
                        }
                        navController.popBackStack()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Save Budgets")
                }
            }
        }
    )
}
