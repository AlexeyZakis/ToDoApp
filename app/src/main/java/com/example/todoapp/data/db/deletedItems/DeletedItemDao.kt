package com.example.todoapp.data.db.deletedItems

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.todoapp.data.db.DbConstants

@Dao
interface DeletedItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItem(item: DeletedItemDb)

    @Delete
    fun deleteItem(item: DeletedItemDb)

    @Query("SELECT * FROM ${DbConstants.DELETED_ITEMS_TABLE_NAME}")
    suspend fun getAll(): List<DeletedItemDb>

    @Query("DELETE FROM ${DbConstants.DELETED_ITEMS_TABLE_NAME}")
    suspend fun deleteAll()
}
