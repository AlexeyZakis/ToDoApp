package com.example.todoapp.data.db.todoItems

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todoapp.data.db.DbConstants

@Database(
    entities = [
        TodoItemDb::class,
    ],
    version = 1,
)
abstract class TodoItemsDatabase : RoomDatabase() {
    abstract fun todoItemDao(): TodoItemDao

    companion object {
        @Volatile
        private var INSTANCE: TodoItemsDatabase? = null

        fun getDatabase(context: Context): TodoItemsDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context = context.applicationContext,
                    klass = TodoItemsDatabase::class.java,
                    name = DbConstants.TODO_ITEMS_DB_NAME
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
