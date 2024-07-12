package com.example.todoapp.data.db.addedItems

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todoapp.data.db.DbConstants

@Database(
    entities = [
        AddedItemDb::class,
    ],
    version = 1,
)
abstract class AddedItemsDatabase : RoomDatabase() {
    abstract fun addedItemDao(): AddedItemDao

    companion object {
        @Volatile
        private var INSTANCE: AddedItemsDatabase? = null

        fun getDatabase(context: Context): AddedItemsDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context = context.applicationContext,
                    klass = AddedItemsDatabase::class.java,
                    name = DbConstants.ADDED_ITEMS_DB_NAME
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
