package com.example.todoapp.data.db.todoItems

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.todoapp.data.db.DbConstants

@Dao
interface TodoItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItem(item: TodoItemDb)

    @Update
    fun updateItem(item: TodoItemDb)

    @Delete
    fun deleteItem(item: TodoItemDb)

    @Query("SELECT * FROM ${DbConstants.TODO_ITEMS_TABLE_NAME}")
    suspend fun getAll(): List<TodoItemDb>

    @Query("DELETE FROM ${DbConstants.TODO_ITEMS_TABLE_NAME}")
    suspend fun deleteAll()

    @Query("SELECT * FROM ${DbConstants.TODO_ITEMS_TABLE_NAME} WHERE id = :id")
    suspend fun getItemById(id: String): TodoItemDb?
}
