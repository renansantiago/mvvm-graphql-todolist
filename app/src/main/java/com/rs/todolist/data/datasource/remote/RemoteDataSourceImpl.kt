package com.rs.todolist.data.datasource.remote

import com.apollographql.apollo.coroutines.await
import com.rs.todolist.*
import com.rs.todolist.data.GraphQlApolloClient
import com.rs.todolist.data.commun.DataSourceException
import com.rs.todolist.data.commun.TodoListResult

class RemoteDataSourceImpl : RemoteDataSource {

    override suspend fun getAccessToken(): TodoListResult<String?> {
        return try {
            val result = GraphQlApolloClient.getAccessToken().await()
            if (result.hasErrors()) {
                TodoListResult.Error(DataSourceException.Server(result.errors?.first()))
            } else {
                TodoListResult.Success(result.data?.generateAccessToken)
            }
        } catch (e: Exception) {
            TodoListResult.Error(DataSourceException.Unexpected(R.string.error_unexpected_message))
        }
    }

    override suspend fun getAllTasks(accessToken: String?): TodoListResult<List<AllTasksQuery.AllTask?>> {
        return try {
            val result = GraphQlApolloClient.getAllTasks(accessToken).await()
            if (result.hasErrors()) {
                TodoListResult.Error(DataSourceException.Server(result.errors?.first()))
            } else {
                TodoListResult.Success(result.data?.allTasks ?: listOf())
            }
        } catch (e: Exception) {
            TodoListResult.Error(DataSourceException.Unexpected(R.string.error_unexpected_message))
        }
    }

    override suspend fun addTask(accessToken: String?, name: String, note: String?, isDone: Boolean?): TodoListResult<CreateTaskMutation.CreateTask?> {
        return try {
            val result = GraphQlApolloClient.addTask(accessToken, name, note, isDone).await()
            if (result.hasErrors()) {
                TodoListResult.Error(DataSourceException.Server(result.errors?.first()))
            } else {
                TodoListResult.Success(result.data?.createTask)
            }
        } catch (e: Exception) {
            TodoListResult.Error(DataSourceException.Unexpected(R.string.error_unexpected_message))
        }
    }

    override suspend fun updateTaskStatus(accessToken: String?, id: String, isDone: Boolean): TodoListResult<UpdateTaskStatusMutation.UpdateTaskStatus?> {
        return try {
            val result = GraphQlApolloClient.updateTaskStatus(accessToken, id, isDone).await()
            if (result.hasErrors()) {
                TodoListResult.Error(DataSourceException.Server(result.errors?.first()))
            } else {
                TodoListResult.Success(result.data?.updateTaskStatus)
            }
        } catch (e: Exception) {
            TodoListResult.Error(DataSourceException.Unexpected(R.string.error_unexpected_message))
        }
    }

    override suspend fun deleteTask(accessToken: String?, id: String): TodoListResult<Boolean?> {
        return try {
            val result = GraphQlApolloClient.deleteTask(accessToken, id).await()
            if (result.hasErrors()) {
                TodoListResult.Error(DataSourceException.Server(result.errors?.first()))
            } else {
                TodoListResult.Success(result.data?.deleteTask)
            }
        } catch (e: Exception) {
            TodoListResult.Error(DataSourceException.Unexpected(R.string.error_unexpected_message))
        }
    }
}
