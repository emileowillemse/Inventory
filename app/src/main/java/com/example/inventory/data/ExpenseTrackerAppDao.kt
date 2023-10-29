//Emileo_Willemse_219275874_Expense_Tracker_App

package com.example.inventory.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

/**
 * Object that has access to the databse, DAO
 */
@Dao
interface ExpenseDao {

    @Query("SELECT * from expense ORDER BY name ASC")
    fun getAllExpense(): Flow<List<Expense>>

    @Query("SELECT * from expense WHERE id = :id")
    fun getExpense(id: Int): Flow<Expense>

    /**
     * All existing items that are added again to the
     * database Room, it will ignore the conflict and
     * add another expense
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(expense: Expense)

    @Update
    suspend fun update(expense: Expense)

    @Delete
    suspend fun delete(expense: Expense)
}