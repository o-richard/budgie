package com.example.budgie.ui.screens


import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Money
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.budgie.data.local.entity.Expense
import com.example.budgie.data.local.entity.CategoryBudget
import com.example.budgie.ui.home.HomeViewModel
import com.example.budgie.ui.navigation.Screen
import com.example.budgie.util.categoryColors
import com.example.budgie.util.categoryIcons
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val currentMonthBudget by viewModel.currentMonthBudget.collectAsState()
    val currentMonthExpenses by viewModel.currentMonthExpenses.collectAsState()
    val groupedExpenses by viewModel.groupedExpenses.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    val currencyFormat = remember { NumberFormat.getCurrencyInstance() }
    val categoryList = listOf("All", "Food", "Transport", "Subscriptions", "Shopping", "Other")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Budgie 🐦", style = MaterialTheme.typography.titleLarge) },
                actions = {
                    IconButton(onClick = { navController.navigate(Screen.Budget.route) }) {
                        Icon(Icons.Default.Settings, contentDescription = "Budget Setup")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(Screen.Expense.route)
            }) {
                Icon(Icons.Default.Add, contentDescription = "Add Expense")
            }
        }
    ) { padding ->
        LazyColumn(
            contentPadding = padding,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            item {
                Spacer(Modifier.height(8.dp))
                Text("This Month's Budgets", style = MaterialTheme.typography.titleMedium)
                BudgetPreviewBars(currentMonthExpenses, currentMonthBudget)
                Spacer(Modifier.height(16.dp))
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, Color.Black)
                        .padding(vertical = 8.dp)
                        .clickable { navController.navigate(Screen.Bills.route) },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(Icons.Default.DateRange, contentDescription = "Recurring Bills")
                    Spacer(Modifier.width(8.dp))
                    Text("View Recurring Bills", style = MaterialTheme.typography.bodyLarge)
                }

                Spacer(Modifier.height(12.dp))
            }

            item {
                CategoryFilterDropdown(categoryList, selectedCategory) {
                    viewModel.selectCategory(it)
                }
                Spacer(Modifier.height(12.dp))
            }

            groupedExpenses.forEach { group ->
                item {
                    Text(
                        group.dateLabel,
                        style = MaterialTheme.typography.labelLarge,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }

                items(group.expenses) { expense ->
                    ExpenseListItem(expense, currencyFormat)
                    HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))
                }
            }

            item {
                Spacer(Modifier.height(80.dp))
            }
        }
    }
}

@Composable
fun CategoryFilterDropdown(
    categories: List<String>,
    selected: String,
    onSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        OutlinedButton(onClick = { expanded = true }) {
            Text("Category: $selected")
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            categories.forEach {
                DropdownMenuItem(text = { Text(it) }, onClick = {
                    onSelected(it)
                    expanded = false
                })
            }
        }
    }
}


@Composable
fun ExpenseListItem(expense: Expense, currencyFormat: NumberFormat) {
    val icon = categoryIcons[expense.category] ?: Icons.Default.Money
    val color = categoryColors[expense.category] ?: Color.Gray
    val dateFormat = remember { SimpleDateFormat("hh:mm a", Locale.getDefault()) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row {
            Icon(icon, contentDescription = null, tint = color)
            Spacer(Modifier.width(8.dp))
            Column {
                Text(expense.note ?: expense.category, fontWeight = FontWeight.Bold)
                Text(dateFormat.format(Date(expense.date)), style = MaterialTheme.typography.bodySmall)
            }
        }

        Column(horizontalAlignment = Alignment.End) {
            Text(currencyFormat.format(expense.amount), fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun BudgetPreviewBars(
    expenses: List<Expense>,
    budgets: List<CategoryBudget>
) {
    val currencyFormat = remember { NumberFormat.getCurrencyInstance() }

    val spendingByCategory = expenses
        .groupBy { it.category }
        .mapValues { (_, list) -> list.sumOf { it.amount } }

    budgets.forEach { budget ->
        val spent = spendingByCategory[budget.category] ?: 0.0
        val progress = (spent / budget.limit).coerceAtMost(1.0)

        Column(modifier = Modifier.padding(vertical = 6.dp)) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(budget.category)
                Text("${currencyFormat.format(spent)} / ${currencyFormat.format(budget.limit)}")
            }

            LinearProgressIndicator(
                progress = { progress.toFloat() },
                modifier = Modifier.fillMaxWidth().height(6.dp),
                color = if (progress > 0.9) Color.Red else categoryColors[budget.category] ?: Color.Gray,
            )
        }
    }
}
