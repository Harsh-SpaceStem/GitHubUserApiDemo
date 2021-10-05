package com.example.githubuserapidemo.room

import android.content.Context
import androidx.room.Room

object DatabaseBuilder {

    private var dbInstance: UserDatabase? = null

    fun getDBInstance(context: Context): UserDatabase {
        if (dbInstance == null) {
            synchronized(UserDatabase::class) {
                dbInstance = buildFocusDB(context)
            }
        }
        return dbInstance!!
    }

    private fun buildFocusDB(context: Context): UserDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            UserDatabase::class.java,
            "FocusDatabase"
        ).build()
    }

}