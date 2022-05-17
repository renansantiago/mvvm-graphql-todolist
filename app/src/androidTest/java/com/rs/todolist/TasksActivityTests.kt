package com.rs.todolist

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.rs.todolist.presentation.tasks.TasksActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class TasksActivityTests {

    @get:Rule
    val activityRule = ActivityScenarioRule(TasksActivity::class.java)

    @Test
    fun testViewsVisibility() {
        onView(withId(R.id.rv_tasks)).check(matches(isDisplayed()))
    }
}
