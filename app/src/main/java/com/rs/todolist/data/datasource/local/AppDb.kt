package com.rs.todolist.data.datasource.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rs.todolist.BuildConfig
import com.rs.todolist.domain.models.TaskModel
import com.rs.todolist.domain.models.UserModel

@Database(entities = [UserModel::class, TaskModel::class], version = 1)
abstract class AppDb : RoomDatabase() {

    abstract fun appDao(): AppDao

    companion object {
        @Volatile
        private var INSTANCE: AppDb? = null

        fun getDatabase(context: Context): AppDb {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDb::class.java,
                    BuildConfig.APPLICATION_ID
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}
