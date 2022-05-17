package com.rs.todolist.data.commun

sealed class TodoListResult<out T> {
    data class Success<out T>(val data: T) : TodoListResult<T>()
    data class Error(val exception: DataSourceException) : TodoListResult<Nothing>()
    object Loading : TodoListResult<Nothing>()
}

inline fun <T : Any> TodoListResult<T>.onSuccess(action: (T) -> Unit): TodoListResult<T> {
    if (this is TodoListResult.Success) action(data)
    return this
}

inline fun <T : Any> TodoListResult<T>.onError(action: (DataSourceException) -> Unit): TodoListResult<T> {
    if (this is TodoListResult.Error) action(exception)
    return this
}

inline fun <T : Any> TodoListResult<T>.onLoading(action: () -> Unit): TodoListResult<T> {
    if (this is TodoListResult.Loading) action()
    return this
}
