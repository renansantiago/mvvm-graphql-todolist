package com.rs.todolist.domain.repository

import com.rs.todolist.data.commun.TodoListResult
import com.rs.todolist.domain.models.TaskModel
import kotlinx.coroutines.flow.Flow

interface AppRepository {
    suspend fun getAccessToken(): Flow<TodoListResult<String?>>
    suspend fun getAllTasks(): Flow<TodoListResult<List<TaskModel?>>>
    suspend fun addTask(name: String, note: String?, isDone: Boolean?): Flow<TodoListResult<TaskModel>>
    suspend fun updateTaskStatus(id: String, isDone: Boolean): Flow<TodoListResult<TaskModel>>
    suspend fun deleteTask(id: String): Flow<TodoListResult<Boolean>>
}
