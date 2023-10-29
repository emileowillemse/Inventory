//Emileo_Willemse_219275874_Expense_Tracker_App

package com.example.inventory

import android.app.Application
import com.example.inventory.data.AppContainer
import com.example.inventory.data.AppDataContainer

class ExpenseTrackerApplication : Application() {

    /**
     * Used to obtain all the dependencies for App Container
     */
    lateinit var container: AppContainer

    override fun onCreate() {

        super.onCreate()
        container = AppDataContainer(this)

    }
}
