package com.rs.todolist.data.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.rs.todolist.domain.models.TaskModel
import com.rs.todolist.domain.models.UserModel

@Dao
interface AppDao {

    @Query("SELECT * FROM task")
    suspend fun getTasks(): List<TaskModel>

    @Insert(onConflict = REPLACE)
    suspend fun saveTasks(tasks: List<TaskModel>)

    @Query("SELECT * FROM user")
    suspend fun getUser(): UserModel?

    @Insert(onConflict = REPLACE)
    suspend fun saveUser(user: UserModel)
}
