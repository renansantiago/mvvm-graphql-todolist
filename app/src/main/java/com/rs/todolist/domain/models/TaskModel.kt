package com.rs.todolist.domain.models

import androidx.room.Entity

@Entity(tableName = "task", primaryKeys = ["id"])
data class TaskModel(
    val id: String,
    val name: String,
    val note: String?,
    val isDone: Boolean
)
