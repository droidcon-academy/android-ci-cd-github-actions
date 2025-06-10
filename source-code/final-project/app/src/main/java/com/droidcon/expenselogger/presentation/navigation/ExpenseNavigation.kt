package com.droidcon.expenselogger.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.droidcon.expenselogger.presentation.screen.ExpenseListScreen
import com.droidcon.expenselogger.presentation.screen.AddExpenseScreen
import com.droidcon.expenselogger.presentation.screen.MonthlyOverviewScreen

@Composable
fun ExpenseNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "expense_list"
    ) {
        composable("expense_list") {
            ExpenseListScreen(
                onNavigateToAddExpense = {
                    navController.navigate("add_expense")
                },
                onNavigateToMonthlyOverview = {
                    navController.navigate("monthly_overview")
                }
            )
        }

        composable("add_expense") {
            AddExpenseScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable("monthly_overview") {
            MonthlyOverviewScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}