package com.example.todoapp.data.db.addedItems

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.todoapp.data.db.DbConstants

@Dao
interface AddedItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItem(item: AddedItemDb)

    @Delete
    fun deleteItem(item: AddedItemDb)

    @Query("SELECT * FROM ${DbConstants.ADDED_ITEMS_TABLE_NAME}")
    suspend fun getAll(): List<AddedItemDb>

    @Query("DELETE FROM ${DbConstants.ADDED_ITEMS_TABLE_NAME}")
    suspend fun deleteAll()
}
