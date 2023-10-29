//Emileo_Willemse_219275874_Expense_Tracker_App

package com.example.inventory.data

import kotlinx.coroutines.flow.Flow

/**
 * Repository that provides insert, update, delete
 * and retrieve all Expenses from the database
 */
interface ExpensesRepository {

    /**
     * Retrieve all expenses from the database
     */
    fun getAllExpenseStream(): Flow<List<Expense>>


    /**
     * Retrieve an expense from the database
     */
    fun getExpenseStream(id: Int): Flow<Expense?>


    /**
     * Insert an expense into the database
     */
    suspend fun insertExpense(expense: Expense)


    /**
     * Delete an expense from the database
     */
    suspend fun deleteExpense(expense: Expense)


    /**
     * Update an expense in the database
     */
    suspend fun updateExpense(expense: Expense)
}
