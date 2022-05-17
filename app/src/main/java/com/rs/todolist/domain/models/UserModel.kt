package com.rs.todolist.domain.models

import androidx.room.Entity

@Entity(tableName = "user", primaryKeys = ["apiKey"])
data class UserModel(
    val apiKey: String,
    val userName: String,
    val accessToken: String?
)
