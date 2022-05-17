package com.rs.todolist.datasource

import com.google.gson.Gson
import com.rs.todolist.AllTasksQuery
import com.rs.todolist.CreateTaskMutation
import com.rs.todolist.UpdateTaskStatusMutation
import com.rs.todolist.data.commun.DataSourceException
import com.rs.todolist.data.commun.TodoListResult
import com.rs.todolist.data.datasource.remote.RemoteDataSource
import com.rs.todolist.helpers.getJson
import com.rs.todolist.utils.fromJsonToObjectType

class RemoteDataSourceImplTest : RemoteDataSource {
    override suspend fun getAccessToken(): TodoListResult<String?> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllTasks(accessToken: String?): TodoListResult<List<AllTasksQuery.AllTask?>> {
        val result =
            Gson().fromJsonToObjectType<AllTasksQuery.Data?>(getJson("list_tasks.json"))
        return if (result != null) {
            TodoListResult.Success(result.allTasks!!)
        } else {
            TodoListResult.Error(DataSourceException.Server(null))
        }
    }

    override suspend fun addTask(accessToken: String?, name: String, note: String?, isDone: Boolean?): TodoListResult<CreateTaskMutation.CreateTask?> {
        TODO("Not yet implemented")
    }

    override suspend fun updateTaskStatus(accessToken: String?, id: String, isDone: Boolean): TodoListResult<UpdateTaskStatusMutation.UpdateTaskStatus?> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTask(accessToken: String?, id: String): TodoListResult<Boolean?> {
        TODO("Not yet implemented")
    }
}
