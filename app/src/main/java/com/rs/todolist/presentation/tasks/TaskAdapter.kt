package com.rs.todolist.presentation.tasks

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rs.todolist.R
import com.rs.todolist.databinding.ItemTaskBinding
import com.rs.todolist.domain.models.TaskModel
import com.rs.todolist.utils.hide
import com.rs.todolist.utils.show

class TaskAdapter(
    private val context: Context,
    private val list: List<TaskModel?>,
    private val updateTaskToDone: (id: String, isDone: Boolean) -> Unit,
    private val deleteTask: (id: String) -> Unit
) :
    RecyclerView.Adapter<TaskAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemTaskBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindTo(list[position])
    }

    override fun getItemCount() = list.size

    inner class ViewHolder(private val view: ItemTaskBinding) :
        RecyclerView.ViewHolder(view.root) {
        init {
            with(view) {
                buttonTaskDone.setOnClickListener {
                    val task = list[adapterPosition]
                    task?.let {
                        updateTaskToDone(it.id, !it.isDone)
                    }
                }
                buttonTaskDelete.setOnClickListener {
                    val task = list[adapterPosition]
                    task?.let {
                        deleteTask(it.id)
                    }
                }
            }
        }

        fun bindTo(taskModel: TaskModel?) {
            with(view) {
                taskModel?.apply {
                    textViewName.text = name
                    textViewNote.text = if (note.isNullOrEmpty()) "-" else note
                    if (isDone) {
                        buttonTaskDone.text = context.getString(R.string.task_list_button_undo)
                        imageViewTaskDone.show()
                    } else {
                        buttonTaskDone.text = context.getString(R.string.task_list_button_done)
                        imageViewTaskDone.hide()
                    }
                }
            }
        }
    }
}
