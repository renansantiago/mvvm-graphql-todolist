package com.rs.todolist.presentation.tasks

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.rs.todolist.databinding.ActivityTasksBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TasksActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTasksBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTasksBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showInitialFragment()
    }

    private fun showInitialFragment() {
        supportFragmentManager.beginTransaction()
            .replace(binding.frameLayoutContainer.id, TasksFragment())
            .commit()
    }

    fun changeFragment(fragment: Fragment, addToBackStack: Boolean = true) {
        supportFragmentManager.beginTransaction()
            .replace(binding.frameLayoutContainer.id, fragment, null)
            .apply { if (addToBackStack) addToBackStack(null) }
            .commit()
    }
}
