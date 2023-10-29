//Emileo_Willemse_219275874_Expense_Tracker_App

package com.example.inventory.ui.Expense

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.inventory.data.Expense
import com.example.inventory.data.ExpensesRepository
import java.text.NumberFormat

/**
 * ViewModel to validate and insert expenses into the database
 */
class ExpenseEntryViewModel(private val expensesRepository: ExpensesRepository) : ViewModel() {

    /**
     * Holds current expense Ui State
     */
    var expenseUiState by mutableStateOf(ExpenseUiState())
        private set

    /**
     * Updates the expense Ui State
     */
    fun updateUiState(expenseDetails: ExpenseDetails) {

        expenseUiState =
            ExpenseUiState(expenseDetails = expenseDetails, isEntryValid = validateInput(expenseDetails))

    }

    /**
     * Inserts an expense into the database
     */
    suspend fun saveExpense() {

        if (validateInput()) {
            expensesRepository.insertExpense(expenseUiState.expenseDetails.toExpense())
        }

    }

    private fun validateInput(uiState: ExpenseDetails = expenseUiState.expenseDetails): Boolean {
        return with(uiState) {

            name.isNotBlank()
            && price.isNotBlank()
            && description.isNotBlank()

        }
    }
}

/**
 * Represents Ui State for Expense
 */
data class ExpenseUiState(

    val expenseDetails: ExpenseDetails = ExpenseDetails(),
    val isEntryValid: Boolean = false

)

data class ExpenseDetails(

    val id: Int = 0,
    val name: String = "",
    val price: String = "",
    val description: String = "",

)


fun ExpenseDetails.toExpense(): Expense = Expense(

    id = id,
    name = name,
    price = price.toDoubleOrNull() ?: 0.0,
    description = description,

)

fun Expense.formatedPrice(): String {

    return NumberFormat.getCurrencyInstance().format(price)

}


fun Expense.toExpenseUiState(isEntryValid: Boolean = false): ExpenseUiState = ExpenseUiState(

    expenseDetails = this.toExpenseDetails(),
    isEntryValid = isEntryValid

)


fun Expense.toExpenseDetails(): ExpenseDetails = ExpenseDetails(

    id = id,
    name = name,
    price = price.toString(),
    description = description,

)
