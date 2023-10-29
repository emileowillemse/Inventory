//Emileo_Willemse_219275874_Expense_Tracker_App

package com.example.inventory.ui.Expense

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inventory.data.ExpensesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * ViewModel to retrieve, update and delete an expense from the database
 */
class ExpenseDetailsViewModel(

    savedStateHandle: SavedStateHandle,
    private val expensesRepository: ExpensesRepository,

) : ViewModel() {

    private val expenseId: Int = checkNotNull(savedStateHandle[ExpenseDetailsDestination.expenseIdArg])

    /**
     * Data gets retrieved from the Expense details and mapped to the UI state
     */
    val uiState: StateFlow<ExpenseDetailsUiState> =
        expensesRepository.getExpenseStream(expenseId)
            .filterNotNull()
            .map {
                ExpenseDetailsUiState(expenseDetails = it.toExpenseDetails())
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = ExpenseDetailsUiState()
            )




    /**
     * Deletes expense in ExpenseTrackerAppRepository database
     */
    suspend fun deleteExpense() {
        expensesRepository.deleteExpense(uiState.value.expenseDetails.toExpense())
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

/**
 * UI State for ExpenseTrackerAppDetailScreen
 */
data class ExpenseDetailsUiState(

    val outOfStock: Boolean = true,
    val expenseDetails: ExpenseDetails = ExpenseDetails()

)
