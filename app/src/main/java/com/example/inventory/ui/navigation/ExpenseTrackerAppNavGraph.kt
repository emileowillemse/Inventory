//Emileo_Willemse_219275874_Expense_Tracker_App

package com.example.inventory.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.inventory.ui.Expense.ExpenseDetailsDestination
import com.example.inventory.ui.Expense.ExpenseDetailsScreen
import com.example.inventory.ui.Expense.ExpenseEditDestination
import com.example.inventory.ui.Expense.ExpenseEditScreen
import com.example.inventory.ui.Expense.ExpenseEntryDestination
import com.example.inventory.ui.Expense.ExpenseEntryScreen
import com.example.inventory.ui.home.HomeDestination
import com.example.inventory.ui.home.HomeScreen

/**
 * Provides Navigation for the application
 */
@Composable
fun ExpenseTrackerNavHost(

    navController: NavHostController,
    modifier: Modifier = Modifier,

) {

    NavHost(

        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier

    ) {

        composable(route = HomeDestination.route) {
            HomeScreen(
                navigateToExpenseEntry = { navController.navigate(ExpenseEntryDestination.route) },
                navigateToExpenseUpdate = {
                    navController.navigate("${ExpenseDetailsDestination.route}/${it}")
                }
            )
        }

        composable(route = ExpenseEntryDestination.route) {
            ExpenseEntryScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }

        composable(
            route = ExpenseDetailsDestination.routeWithArgs,
            arguments = listOf(navArgument(ExpenseDetailsDestination.expenseIdArg) {
                type = NavType.IntType
            })
        ) {
            ExpenseDetailsScreen(
                navigateToEditExpense = { navController.navigate("${ExpenseEditDestination.route}/$it") },
                navigateBack = { navController.navigateUp() }
            )
        }

        composable(
            route = ExpenseEditDestination.routeWithArgs,
            arguments = listOf(navArgument(ExpenseEditDestination.expenseAppIdArg) {
                type = NavType.IntType
            })
        ) {
            ExpenseEditScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }
    }
}