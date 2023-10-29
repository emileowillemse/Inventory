//Emileo_Willemse_219275874_Expense_Tracker_App

package com.example.inventory.data

import android.content.Context

/**
 * ExpenseTrackerApp container for Dependency injection
 */
interface AppContainer {

    val expensesRepository: ExpensesRepository

}

/**
 *AppContainer that provides an implementation for the instance of OfflineExpenseTrackerAppRepository
 */
class AppDataContainer(private val context: Context) : AppContainer {

    /**
     *Implementation for ExpenseTrackerAppRepository
     */
    override val expensesRepository: ExpensesRepository by lazy {

        OfflineExpensesRepository(ExpenseTrackerDatabase.getDatabase(context).expenseDao())

    }
}
