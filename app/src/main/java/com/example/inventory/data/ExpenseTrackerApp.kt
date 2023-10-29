//Emileo_Willemse_219275874_Expense_Tracker_App

package com.example.inventory.data

import androidx.room.Entity
import androidx.room.PrimaryKey


/**
 * Entity data class which represents a single row in the database
 */
@Entity(tableName = "expense")

data class Expense(

    @PrimaryKey(autoGenerate = true)

    /**
     * ExpenseTrackerApp items that will be displayed on the database as the primary items
     */
    val id: Int = 0,
    val name: String,
    val price: Double,
    val description: String

)