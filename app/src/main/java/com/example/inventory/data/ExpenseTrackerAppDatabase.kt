//Emileo_Willemse_219275874_Expense_Tracker_App

package com.example.inventory.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Database class with a Instance object
 */
@Database(entities = [Expense::class], version = 1, exportSchema = false)

abstract class ExpenseTrackerDatabase : RoomDatabase() {

    abstract fun expenseDao(): ExpenseDao

    companion object {
        @Volatile
        private var Instance: ExpenseTrackerDatabase? = null

        fun getDatabase(context: Context): ExpenseTrackerDatabase {
             return Instance ?: synchronized(this) {

                 /**
                  * Room will delete all the data in the tables
                  * in the database when it attempts to perform
                  * a migration with no defined migration path.
                  */
                Room.databaseBuilder(context, ExpenseTrackerDatabase::class.java, "expense_database")

                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}
