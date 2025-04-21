package com.example.budgie.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.budgie.ui.screens.BudgetSetupScreen
import com.example.budgie.ui.screens.WelcomeScreen
import com.example.budgie.ui.screens.HomeScreen
import com.example.budgie.ui.screens.ExpenseInputScreen
import com.example.budgie.ui.screens.RecurringBillInputScreen
import com.example.budgie.ui.screens.RecurringBillScreen

sealed class Screen(val route: String) {
    data object Welcome : Screen("welcome")
    data object Home : Screen("home")
    data object Expense : Screen("expense")
    data object Bills : Screen("bills")
    data object BillsAdd : Screen("billsAdd")
    data object Budget : Screen("budget")
}

@Composable
fun BudgieNavGraph(navController: NavHostController) {
    NavHost(navController, startDestination = Screen.Welcome.route) {
        composable(Screen.Welcome.route) {  WelcomeScreen(navController) }
        composable(Screen.Home.route) {  HomeScreen(navController) }
        composable(Screen.Expense.route) { ExpenseInputScreen(navController) }
        composable(Screen.Bills.route) { RecurringBillScreen(navController) }
        composable(Screen.BillsAdd.route) { RecurringBillInputScreen(navController) }
        composable(Screen.Budget.route) { BudgetSetupScreen(navController) }
    }
}
