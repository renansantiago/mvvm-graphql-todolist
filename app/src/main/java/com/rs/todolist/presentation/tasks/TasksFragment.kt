package com.rs.todolist.presentation.tasks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import com.apollographql.apollo.api.Error
import com.rs.todolist.data.commun.onError
import com.rs.todolist.data.commun.onLoading
import com.rs.todolist.data.commun.onSuccess
import com.rs.todolist.databinding.FragmentTasksBinding
import com.rs.todolist.domain.models.TaskModel
import com.rs.todolist.presentation.taskadd.TaskAddFragment
import com.rs.todolist.utils.hide
import com.rs.todolist.utils.show
import com.rs.todolist.utils.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TasksFragment : Fragment() {

    private val viewModel: TasksViewModel by viewModels()
    private lateinit var binding: FragmentTasksBinding
    private val parentActivity by lazy { (activity as? TasksActivity) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentTasksBinding.inflate(inflater, container, false)
        initObservers()
        setupUI()
        viewModel.getAllTasks()
        return binding.root
    }

    private fun initObservers() {
        //get all tasks
        viewModel.resultListAllTasks.observe(requireActivity()) {
            it.onSuccess { list ->
                binding.progressCircular.hide()
                setListTasks(list)
            }.onError { error ->
                binding.progressCircular.hide()
                binding.textViewEmpty.show()
                when (error.messageResource) {
                    is Int -> activity?.toast(getString(error.messageResource))
                    is Error? -> {
                        error.messageResource?.let { errorMessage -> activity?.toast(errorMessage.message) }
                    }
                }
            }.onLoading {
                binding.textViewEmpty.hide()
                binding.progressCircular.show()
            }
        }

        //update task status
        viewModel.resultUpdateTaskStatus.observe(requireActivity()) {
            it.onSuccess {
                binding.progressCircular.hide()
                //TODO - UPDATE THE ITEM LIST
                viewModel.getAllTasks()
            }.onError { error ->
                binding.progressCircular.hide()
                when (error.messageResource) {
                    is Int -> activity?.toast(getString(error.messageResource))
                    is Error? -> {
                        error.messageResource?.let { errorMessage -> activity?.toast(errorMessage.message) }
                    }
                }
            }.onLoading {
                binding.progressCircular.show()
            }
        }

        //delete task
        viewModel.resultDeleteTask.observe(requireActivity()) {
            it.onSuccess {
                binding.progressCircular.hide()
                //TODO - UPDATE THE ITEM LIST
                viewModel.getAllTasks()
            }.onError { error ->
                binding.progressCircular.hide()
                when (error.messageResource) {
                    is Int -> activity?.toast(getString(error.messageResource))
                    is Error? -> {
                        error.messageResource?.let { errorMessage -> activity?.toast(errorMessage.message) }
                    }
                }
            }.onLoading {
                binding.progressCircular.show()
            }
        }
    }

    private fun setListTasks(tasks: List<TaskModel?>) {
        with(binding.rvTasks) {
            adapter = TaskAdapter(context, tasks, ::updateTaskToDone, ::deleteTask)
        }
        if (tasks.isEmpty()) {
            binding.textViewEmpty.show()
        } else {
            binding.textViewEmpty.hide()
        }
    }

    private fun updateTaskToDone(id: String, isDone: Boolean) {
        viewModel.updateTaskStatus(id, isDone)
    }

    private fun deleteTask(id: String) {
        viewModel.deleteTask(id)
    }

    private fun setupUI() {
        with(binding) {
            rvTasks.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

            buttonTasksRefresh.setOnClickListener {
                viewModel.getAllTasks()
            }
            buttonTasksAdd.setOnClickListener {
                parentActivity?.changeFragment(TaskAddFragment())
            }
        }
    }
}
