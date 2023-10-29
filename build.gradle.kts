//Emileo_Willemse_219275874_Expense_Tracker_App

buildscript {
    extra.apply {

        set("room_version", "2.5.2")

    }
}

plugins {

    id("com.android.application") version "8.1.1" apply false
    id("org.jetbrains.kotlin.android") version "1.8.21" apply false
    id("com.android.library") version "8.1.1" apply false
    id("com.google.devtools.ksp") version "1.8.21-1.0.11"

}

tasks.register("clean", Delete::class) {

    delete(rootProject.buildDir)

}
