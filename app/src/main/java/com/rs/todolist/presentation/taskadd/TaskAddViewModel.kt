package com.rs.todolist.presentation.taskadd

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rs.todolist.data.commun.TodoListResult
import com.rs.todolist.domain.models.TaskModel
import com.rs.todolist.domain.repository.AppRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class TaskAddViewModel @ViewModelInject constructor(
    private val appRepository: AppRepository
) : ViewModel() {

    val resultTaskAdded = MutableLiveData<TodoListResult<TaskModel>>()

    fun isFieldNameValid(name: String): Boolean {
        return when {
            name.length < 3 -> {
                false
            }
            else -> true
        }
    }

    fun addTask(name: String, note: String?, isDone: Boolean?) {
        viewModelScope.launch {
            appRepository.addTask(name, note, isDone).collect {
                resultTaskAdded.postValue(it)
            }
        }
    }
}
