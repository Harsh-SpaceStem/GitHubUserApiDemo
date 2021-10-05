package com.example.githubuserapidemo.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "bookmarkedUsers")
data class BookmarkedUsers(
    @PrimaryKey
    val id: Int,
    val login: String,
    val avatar_url: String,

    ) : Serializable