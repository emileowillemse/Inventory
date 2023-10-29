//Emileo_Willemse_219275874_Expense_Tracker_App

package com.example.inventory.ui.Expense

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inventory.data.ExpensesRepository
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

/**
 * ViewModel to retrieve and update an expense from the database
 */
class ExpenseEditViewModel(

    savedStateHandle: SavedStateHandle,
    private val expensesRepository: ExpensesRepository

) : ViewModel() {

    /**
     * Holds current expense UI State
     */
    var expenseUiState by mutableStateOf(ExpenseUiState())
        private set

    private val expenseId: Int = checkNotNull(savedStateHandle[ExpenseEditDestination.expenseAppIdArg])

    init {
        viewModelScope.launch {

            expenseUiState = expensesRepository.getExpenseStream(expenseId)
                .filterNotNull()
                .first()
                .toExpenseUiState(true)

        }
    }

    /**
     * Updates the Expense in the database
     */
    suspend fun updateExpense() {

        if (validateInput(expenseUiState.expenseDetails)) {
            expensesRepository.updateExpense(expenseUiState.expenseDetails.toExpense())

        }
    }

    fun updateUiState(expenseDetails: ExpenseDetails) {

        expenseUiState =
            ExpenseUiState(expenseDetails = expenseDetails, isEntryValid = validateInput(expenseDetails))

    }

    private fun validateInput(uiState: ExpenseDetails = expenseUiState.expenseDetails): Boolean {

        return with(uiState) {
            name.isNotBlank()
            && price.isNotBlank()
            && description.isNotBlank()

        }
    }
}
