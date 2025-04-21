package com.example.budgie.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.budgie.ui.bills.RecurringBillViewModel
import com.example.budgie.ui.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecurringBillScreen(
    navController: NavController,
    viewModel: RecurringBillViewModel = hiltViewModel()
) {
    val bills by viewModel.bills.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Recurring Bills", style = MaterialTheme.typography.titleLarge) },
                actions = {
                    IconButton(onClick = { navController.navigate(Screen.BillsAdd.route) }) {
                        Icon(Icons.Default.Add, contentDescription = "Recurring Bill Setup")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            contentPadding = padding,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            items(bills) { bill ->
                Card(modifier = Modifier.padding(8.dp).fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(bill.name, style = MaterialTheme.typography.labelLarge)
                        Text("Amount: \$${bill.amount}")
                        Text("Due: ${bill.dueDate}")
                        Text("Frequency: ${bill.frequency}")
                        Button(onClick = { viewModel.deleteBill(bill) }, Modifier.fillMaxWidth()) {
                            Text("Delete")
                        }
                    }
                }
            }
        }
    }
}
