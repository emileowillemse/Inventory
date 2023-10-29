//Emileo_Willemse_219275874_Expense_Tracker_App

package com.example.inventory.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inventory.data.Expense
import com.example.inventory.data.ExpensesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

/**
 * ViewModel to retrieve all expenses from the database
 */
class HomeViewModel(expensesRepository: ExpensesRepository) : ViewModel() {


    val homeUiState: StateFlow<HomeUiState> =

        expensesRepository.getAllExpenseStream().map { HomeUiState(it) }

            .stateIn(

                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = HomeUiState()

            )

    companion object {

        private const val TIMEOUT_MILLIS = 5_000L

    }
}

/**
 * UI State for Home Screen
 */
data class HomeUiState(val expenseList: List<Expense> = listOf())
