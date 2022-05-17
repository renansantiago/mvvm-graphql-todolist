package com.rs.todolist.repository

import com.rs.todolist.data.commun.TodoListResult
import com.rs.todolist.data.mappers.mapToDomainModel
import com.rs.todolist.datasource.RemoteDataSourceImplTest
import com.rs.todolist.domain.models.TaskModel
import com.rs.todolist.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AppRepositoryImplTest(private val dataSourceImplTest: RemoteDataSourceImplTest) :
    AppRepository {
    override suspend fun getAccessToken(): Flow<TodoListResult<String?>> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllTasks(): Flow<TodoListResult<List<TaskModel?>>> {
        return flow {
            dataSourceImplTest.getAllTasks(null).run {
                when (this) {
                    is TodoListResult.Success -> {
                            emit(TodoListResult.Success(data.mapNotNull { task -> task?.mapToDomainModel() }))
                    }
                    is TodoListResult.Error -> {
                        emit(TodoListResult.Error(exception))
                    }
                    else -> {}
                }
            }
        }
    }

    override suspend fun addTask(name: String, note: String?, isDone: Boolean?): Flow<TodoListResult<TaskModel>> {
        TODO("Not yet implemented")
    }

    override suspend fun updateTaskStatus(id: String, isDone: Boolean): Flow<TodoListResult<TaskModel>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTask(id: String): Flow<TodoListResult<Boolean>> {
        TODO("Not yet implemented")
    }
}
