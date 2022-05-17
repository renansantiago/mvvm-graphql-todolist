package com.rs.todolist.presentation.taskadd

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.apollographql.apollo.api.Error
import com.rs.todolist.R
import com.rs.todolist.data.commun.onError
import com.rs.todolist.data.commun.onLoading
import com.rs.todolist.data.commun.onSuccess
import com.rs.todolist.databinding.FragmentTaskAddBinding
import com.rs.todolist.presentation.tasks.TasksActivity
import com.rs.todolist.utils.hide
import com.rs.todolist.utils.show
import com.rs.todolist.utils.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TaskAddFragment : Fragment() {

    private val viewModel: TaskAddViewModel by viewModels()
    private lateinit var binding: FragmentTaskAddBinding
    private val parentActivity by lazy { (activity as? TasksActivity) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentTaskAddBinding.inflate(inflater, container, false)
        initObserver()
        setupUI()
        return binding.root
    }

    private fun initObserver() {
        viewModel.resultTaskAdded.observe(requireActivity()) {
            it.onSuccess {
                binding.progressCircular.hide()
                parentActivity?.onBackPressed()
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

    private fun setupUI() {
        binding.buttonTasksAdd.setOnClickListener {
            handleAddTask()
        }
    }

    private fun handleAddTask() {
        val name = binding.editTextName.text.toString()
        val note = binding.editTextNote.text.toString()
        //TODO - PUT A CHECKBOX TO THE USER HAVE A OPTION TO ADD A DONE TASK
        val isDone = false

        if (viewModel.isFieldNameValid(name)) {
            viewModel.addTask(name, note, isDone)
        } else {
            activity?.toast(getString(R.string.task_add_name_error))
        }
    }
}
