package com.rs.todolist.data.repository

import com.rs.todolist.data.commun.API_KEY
import com.rs.todolist.data.commun.TodoListResult
import com.rs.todolist.data.commun.USER_NAME_KEY
import com.rs.todolist.data.datasource.local.AppDao
import com.rs.todolist.data.datasource.remote.RemoteDataSource
import com.rs.todolist.data.mappers.mapToDomainModel
import com.rs.todolist.domain.models.TaskModel
import com.rs.todolist.domain.models.UserModel
import com.rs.todolist.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart

class AppRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val appDao: AppDao
) : AppRepository {

    override suspend fun getAccessToken(): Flow<TodoListResult<String?>> =
        flow {
            when (val result = remoteDataSource.getAccessToken()) {
                is TodoListResult.Success -> {
                    val accessToken = result.data
                    appDao.saveUser(
                        UserModel(
                            API_KEY,
                            USER_NAME_KEY,
                            accessToken
                        )
                    )
                    emit(TodoListResult.Success(accessToken))
                }
                is TodoListResult.Error -> emit(TodoListResult.Error(result.exception))
                TodoListResult.Loading -> {}
            }
        }.onStart { emit(TodoListResult.Loading) }

    override suspend fun getAllTasks(): Flow<TodoListResult<List<TaskModel?>>> =
        flow {
            when (val result = remoteDataSource.getAllTasks(
                handleAccessToken(appDao.getUser()?.accessToken)
            )) {
                is TodoListResult.Success -> {
                    val tasks = result.data.mapNotNull { task -> task?.mapToDomainModel() }
                    appDao.saveTasks(tasks)
                    emit(TodoListResult.Success(tasks))
                }
                is TodoListResult.Error -> {
                    val tasks = appDao.getTasks()
                    if (tasks.isNotEmpty()) {
                        emit(TodoListResult.Success(tasks))
                    } else {
                        emit(TodoListResult.Error(result.exception))
                    }
                }
                TodoListResult.Loading -> {}
            }
        }.onStart { emit(TodoListResult.Loading) }

    override suspend fun addTask(name: String, note: String?, isDone: Boolean?): Flow<TodoListResult<TaskModel>> =
        flow {
            when (val result = remoteDataSource.addTask(
                handleAccessToken(appDao.getUser()?.accessToken),
                name,
                note,
                isDone
            )) {
                is TodoListResult.Success -> {
                    result.data?.let {
                        it.mapToDomainModel().apply {
                            emit(TodoListResult.Success(this))
                        }
                    }
                }
                is TodoListResult.Error -> emit(TodoListResult.Error(result.exception))
                TodoListResult.Loading -> {}
            }
        }.onStart { emit(TodoListResult.Loading) }

    override suspend fun updateTaskStatus(id: String, isDone: Boolean): Flow<TodoListResult<TaskModel>> =
        flow {
            when (val result = remoteDataSource.updateTaskStatus(
                handleAccessToken(appDao.getUser()?.accessToken),
                id,
                isDone
            )) {
                is TodoListResult.Success -> {
                    result.data?.let {
                        it.mapToDomainModel().apply {
                            emit(TodoListResult.Success(this))
                        }
                    }
                }
                is TodoListResult.Error -> emit(TodoListResult.Error(result.exception))
                TodoListResult.Loading -> {}
            }
        }.onStart { emit(TodoListResult.Loading) }

    override suspend fun deleteTask(id: String): Flow<TodoListResult<Boolean>> =
        flow {
            when (val result = remoteDataSource.deleteTask(
                handleAccessToken(appDao.getUser()?.accessToken),
                id
            )) {
                is TodoListResult.Success -> {
                    result.data?.let {
                        emit(TodoListResult.Success(it))
                    }
                }
                is TodoListResult.Error -> emit(TodoListResult.Error(result.exception))
                TodoListResult.Loading -> {}
            }
        }.onStart { emit(TodoListResult.Loading) }

    //TODO - IMPROVE WAY TO HANDLE ACCESS TOKEN
    private suspend fun handleAccessToken(accessToken: String?): String? {
        var token = accessToken
        if (accessToken == null) {
            when (val result = remoteDataSource.getAccessToken()) {
                is TodoListResult.Success -> {
                    token = result.data
                    appDao.saveUser(
                        UserModel(
                            API_KEY,
                            USER_NAME_KEY,
                            token
                        )
                    )
                }
                else -> {}
            }
        }
        return token
    }
}
