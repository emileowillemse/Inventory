//Emileo_Willemse_219275874_Expense_Tracker_App

package com.example.inventory.data

import kotlinx.coroutines.flow.Flow

class OfflineExpensesRepository(private val expenseDao: ExpenseDao) : ExpensesRepository {

    override fun getAllExpenseStream(): Flow<List<Expense>> = expenseDao.getAllExpense()

    override fun getExpenseStream(id: Int): Flow<Expense?> = expenseDao.getExpense(id)

    override suspend fun insertExpense(expense: Expense) = expenseDao.insert(expense)

    override suspend fun deleteExpense(expense: Expense) = expenseDao.delete(expense)

    override suspend fun updateExpense(expense: Expense) = expenseDao.update(expense)
}
