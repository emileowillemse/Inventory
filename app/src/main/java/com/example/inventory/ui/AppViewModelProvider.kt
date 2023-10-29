//Emileo_Willemse_219275874_Expense_Tracker_App

package com.example.inventory.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.inventory.ExpenseTrackerApplication
import com.example.inventory.ui.Expense.ExpenseDetailsViewModel
import com.example.inventory.ui.home.HomeViewModel
import com.example.inventory.ui.Expense.ExpenseEditViewModel
import com.example.inventory.ui.Expense.ExpenseEntryViewModel


/**
 * Provides factory to create instance of viewmodel for the entire app
 */
object AppViewModelProvider {
    val Factory = viewModelFactory {

        /**
         * Initializer for ExpenseEditViewModel
         */
        initializer {

            ExpenseEditViewModel(
                this.createSavedStateHandle(),
                inventoryApplication().container.expensesRepository
            )

        }

        /**
         * Initializer for ExpenseEntryViewModel
         */
        initializer {

            ExpenseEntryViewModel(inventoryApplication().container.expensesRepository)

        }

        /**
         * Initializer for ExpenseDetailsViewModel
         */
        initializer {

            ExpenseDetailsViewModel(
                this.createSavedStateHandle(),
                inventoryApplication().container.expensesRepository

            )
        }

        /**
         * Initializer for HomeViewModel
         */
        initializer {

            HomeViewModel(inventoryApplication().container.expensesRepository)

        }
    }
}

fun CreationExtras.inventoryApplication(): ExpenseTrackerApplication =

    (this[AndroidViewModelFactory.APPLICATION_KEY] as ExpenseTrackerApplication)
