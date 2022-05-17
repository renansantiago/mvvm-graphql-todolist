package com.rs.todolist.viewModels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.rs.todolist.datasource.RemoteDataSourceImplTest
import com.rs.todolist.helpers.MainCoroutineRule
import com.rs.todolist.presentation.taskadd.TaskAddViewModel
import com.rs.todolist.repository.AppRepositoryImplTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class TaskAddViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: TaskAddViewModel

    @Before
    fun setup() {
        viewModel = TaskAddViewModel(
            AppRepositoryImplTest(RemoteDataSourceImplTest())
        )
    }

    @Test
    fun taskAddisFieldNameInvalidTest() {
        assertThat(viewModel.isFieldNameValid("")).isFalse()
    }

    @Test
    fun taskAddisFieldNameValidTest() {
        assertThat(viewModel.isFieldNameValid("Task 1")).isTrue()
    }
}
