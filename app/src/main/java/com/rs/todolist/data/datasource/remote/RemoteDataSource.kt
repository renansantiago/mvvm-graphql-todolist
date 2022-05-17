package com.rs.todolist.data.datasource.remote

import com.rs.todolist.*
import com.rs.todolist.data.commun.TodoListResult

interface RemoteDataSource {
    suspend fun getAccessToken(): TodoListResult<String?>
    suspend fun getAllTasks(accessToken: String?): TodoListResult<List<AllTasksQuery.AllTask?>>
    suspend fun addTask(accessToken: String?, name: String, note: String?, isDone: Boolean?): TodoListResult<CreateTaskMutation.CreateTask?>
    suspend fun updateTaskStatus(accessToken: String?, id: String, isDone: Boolean): TodoListResult<UpdateTaskStatusMutation.UpdateTaskStatus?>
    suspend fun deleteTask(accessToken: String?, id: String): TodoListResult<Boolean?>
}
