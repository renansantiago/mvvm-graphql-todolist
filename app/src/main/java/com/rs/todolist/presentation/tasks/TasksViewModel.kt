package com.rs.todolist.presentation.tasks

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rs.todolist.data.commun.TodoListResult
import com.rs.todolist.domain.models.TaskModel
import com.rs.todolist.domain.repository.AppRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class TasksViewModel @ViewModelInject constructor(
    private val appRepository: AppRepository
) : ViewModel() {

    val resultListAllTasks = MutableLiveData<TodoListResult<List<TaskModel?>>>()
    val resultUpdateTaskStatus = MutableLiveData<TodoListResult<TaskModel>>()
    val resultDeleteTask = MutableLiveData<TodoListResult<Boolean>>()

    fun getAllTasks() {
        viewModelScope.launch {
            appRepository.getAllTasks().collect {
                resultListAllTasks.postValue(it)
            }
        }
    }

    fun updateTaskStatus(id: String, isDone: Boolean) {
        viewModelScope.launch {
            appRepository.updateTaskStatus(id, isDone).collect {
                resultUpdateTaskStatus.postValue(it)
            }
        }
    }

    fun deleteTask(id: String) {
        viewModelScope.launch {
            appRepository.deleteTask(id).collect {
                resultDeleteTask.postValue(it)
            }
        }
    }
}
