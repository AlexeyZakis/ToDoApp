package com.example.todoapp.data.db.deletedItems

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todoapp.data.db.DbConstants

@Database(
    entities = [
        DeletedItemDb::class,
    ],
    version = 1,
)
abstract class DeletedItemsDatabase : RoomDatabase() {
    abstract fun deletedItemDao(): DeletedItemDao

    companion object {
        @Volatile
        private var INSTANCE: DeletedItemsDatabase? = null

        fun getDatabase(context: Context): DeletedItemsDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context = context.applicationContext,
                    klass = DeletedItemsDatabase::class.java,
                    name = DbConstants.DELETED_ITEMS_DB_NAME
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
