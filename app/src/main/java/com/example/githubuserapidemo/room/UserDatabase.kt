package com.example.githubuserapidemo.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.githubuserapidemo.model.BookmarkedUsers

@Database(
    entities = [BookmarkedUsers::class],
    version = 1
)
abstract class UserDatabase : RoomDatabase() {
    abstract fun getUserDao(): UserDao
}