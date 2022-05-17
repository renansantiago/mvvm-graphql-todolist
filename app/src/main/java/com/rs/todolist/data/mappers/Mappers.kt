package com.rs.todolist.data.mappers

import com.rs.todolist.*
import com.rs.todolist.domain.models.*

fun AllTasksQuery.AllTask.mapToDomainModel() = TaskModel(
    id,
    name,
    note ?: "",
    isDone
)

fun CreateTaskMutation.CreateTask.mapToDomainModel() = TaskModel(
    id,
    name,
    note ?: "",
    isDone
)

fun UpdateTaskStatusMutation.UpdateTaskStatus.mapToDomainModel() = TaskModel(
    id,
    name,
    note ?: "",
    isDone
)
