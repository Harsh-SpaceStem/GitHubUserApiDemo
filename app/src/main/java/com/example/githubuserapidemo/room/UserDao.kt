package com.example.githubuserapidemo.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.githubuserapidemo.model.BookmarkedUsers

@Dao
interface UserDao {
    @Query("Select * from bookmarkedUsers")
    fun getAllBookmarkedUsers(): List<BookmarkedUsers>

    @Insert
    fun bookmarkThisUser(userToBookmark: BookmarkedUsers)

    @Delete
    fun unbookmarkThisUser(userToBookmark: BookmarkedUsers)
}