package com.example.budgie.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType.Companion.PrimaryNotEditable
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.budgie.data.local.entity.RecurringBill
import com.example.budgie.ui.bills.RecurringBillViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecurringBillInputScreen(
    navController: NavController,
    recurringBillViewModel: RecurringBillViewModel = hiltViewModel()
) {
    var name by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var frequency by remember { mutableStateOf("Monthly") }
    var dueDate by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var reminderDaysBefore by remember { mutableIntStateOf(0) }
    var autoMarkAsPaid by remember { mutableStateOf(false) }
    var expandedCategories by remember { mutableStateOf(false) }
    var expandedFrequency by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Recurring Bill", style = MaterialTheme.typography.titleLarge) }
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier.padding(padding).padding(16.dp)
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Bill Name") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = amount,
                    onValueChange = { amount = it },
                    label = { Text("Amount") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                )

                ExposedDropdownMenuBox(
                    expanded = expandedFrequency,
                    onExpandedChange = { expandedFrequency = it },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = frequency,
                        onValueChange = { },
                        label = { Text("Frequency") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedFrequency)
                        },
                        readOnly = true,
                        modifier = Modifier.fillMaxWidth().menuAnchor(PrimaryNotEditable)
                    )

                    ExposedDropdownMenu(
                        expanded = expandedFrequency,
                        onDismissRequest = { expandedFrequency = false }
                    ) {
                        listOf("Monthly").forEach { label ->
                            DropdownMenuItem(
                                text = { Text(label) },
                                onClick = {
                                    frequency = label
                                    expandedFrequency = false
                                }
                            )
                        }
                    }
                }

                OutlinedTextField(
                    value = dueDate,
                    onValueChange = { dueDate = it },
                    label = { Text("Due Day Of Month") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                )

                ExposedDropdownMenuBox(
                    expanded = expandedCategories,
                    onExpandedChange = { expandedCategories = it },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = category,
                        onValueChange = { },
                        label = { Text("Category") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedCategories)
                        },
                        readOnly = true,
                        modifier = Modifier.fillMaxWidth().menuAnchor(PrimaryNotEditable)
                    )

                    ExposedDropdownMenu(
                        expanded = expandedCategories,
                        onDismissRequest = { expandedCategories = false }
                    ) {
                        listOf("Food", "Transport", "Subscriptions", "Shopping", "Other").forEach { label ->
                            DropdownMenuItem(
                                text = { Text(label) },
                                onClick = {
                                    category = label
                                    expandedCategories = false
                                }
                            )
                        }
                    }
                }

                OutlinedTextField(
                    value = reminderDaysBefore.toString(),
                    onValueChange = { reminderDaysBefore = it.toIntOrNull() ?: 0 },
                    label = { Text("Reminder (Days Before)") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Auto Mark as Paid")
                    Switch(checked = autoMarkAsPaid, onCheckedChange = { autoMarkAsPaid = it })
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        if (name.isNotEmpty() && amount.isNotEmpty() && dueDate.isNotEmpty() && category.isNotEmpty()) {
                            val bill = RecurringBill(
                                name = name,
                                amount = amount.toFloat(),
                                frequency = frequency,
                                dueDate = dueDate.toInt(),
                                category = category,
                                reminderDaysBefore = reminderDaysBefore,
                                autoMarkAsPaid = autoMarkAsPaid
                            )
                            recurringBillViewModel.addBill(bill)
                            navController.popBackStack()
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Save Bill")
                }
            }
        }
    )
}
