package com.rs.todolist.viewModels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.rs.todolist.data.commun.onError
import com.rs.todolist.data.commun.onSuccess
import com.rs.todolist.datasource.RemoteDataSourceImplTest
import com.rs.todolist.helpers.MainCoroutineRule
import com.rs.todolist.helpers.runBlockingTest
import com.rs.todolist.presentation.tasks.TasksViewModel
import com.rs.todolist.repository.AppRepositoryImplTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class TasksViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: TasksViewModel

    @Before
    fun setup() {
        viewModel = TasksViewModel(
            AppRepositoryImplTest(RemoteDataSourceImplTest())
        )
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `Test getAllTasks should return success`() = mainCoroutineRule.runBlockingTest {
        viewModel.getAllTasks()

        viewModel.resultListAllTasks.value?.onSuccess { result ->
            assertThat(result).isNotEmpty()
            assertThat(result[0]?.name).matches("task 1")
            assertThat(result[0]?.isDone).isTrue()
        }?.onError { error ->
            assertThat(error).isNull()
        }
    }
}
