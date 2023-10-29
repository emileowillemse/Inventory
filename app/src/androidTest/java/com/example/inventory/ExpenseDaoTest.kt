//Emileo_Willemse_219275874_Expense_Tracker_App

package com.example.inventory

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.inventory.data.ExpenseTrackerDatabase
import com.example.inventory.data.Expense
import com.example.inventory.data.ExpenseDao
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class ExpenseDaoTest {

    private lateinit var expenseDao: ExpenseDao
    private lateinit var expenseTrackerDatabase: ExpenseTrackerDatabase
    private val expense1 = Expense(1, "Apples", 10.0, 20, "")
    private val expense2 = Expense(2, "Bananas", 15.0, 97, "")

    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        expenseTrackerDatabase = Room.inMemoryDatabaseBuilder(context, ExpenseTrackerDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        expenseDao = expenseTrackerDatabase.expenseDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        expenseTrackerDatabase.close()
    }

    @Test
    @Throws(Exception::class)
    fun daoInsert_insertsExpenseIntoDB() = runBlocking {
        addOneExpenseToDb()
        val allItems = expenseDao.getAllExpense().first()
        assertEquals(allItems[0], expense1)
    }

    @Test
    @Throws(Exception::class)
    fun daoGetAllExpense_returnsAllItemsFromDB() = runBlocking {
        addTwoItemsToDb()
        val allItems = expenseDao.getAllExpense().first()
        assertEquals(allItems[0], expense1)
        assertEquals(allItems[1], expense2)
    }


    @Test
    @Throws(Exception::class)
    fun daoGetItem_returnsItemFromDB() = runBlocking {
        addOneExpenseToDb()
        val item = expenseDao.getExpense(1)
        assertEquals(item.first(), expense1)
    }

    @Test
    @Throws(Exception::class)
    fun daoDeleteItems_deletesAllItemsFromDB() = runBlocking {
        addTwoItemsToDb()
        expenseDao.delete(expense1)
        expenseDao.delete(expense2)
        val allItems = expenseDao.getAllExpense().first()
        assertTrue(allItems.isEmpty())
    }

    @Test
    @Throws(Exception::class)
    fun daoUpdateExpense_updatesExpenseInDB() = runBlocking {
        addTwoItemsToDb()
        expenseDao.update(Expense(1, "Apples", 15.0, 25, ""))
        expenseDao.update(Expense(2, "Bananas", 5.0, 50, ""))

        val allExpense = expenseDao.getAllExpense().first()
        assertEquals(allExpense[0], Expense(1, "Apples", 15.0, 25, ""))
        assertEquals(allExpense[1], Expense(2, "Bananas", 5.0, 50, ""))
    }

    private suspend fun addOneExpenseToDb() {
        expenseDao.insert(expense1)
    }

    private suspend fun addTwoItemsToDb() {
        expenseDao.insert(expense1)
        expenseDao.insert(expense2)
    }
}
