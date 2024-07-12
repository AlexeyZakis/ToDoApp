package com.example.todoapp.data.db.todoItems

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.todoapp.data.db.DbConstants

@Entity(tableName = DbConstants.TODO_ITEMS_TABLE_NAME)
class TodoItemDb(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "text")
    val taskText: String,
    @ColumnInfo(name = "creationDate")
    val creationDate: Long,
    @ColumnInfo(name = "priority")
    val priority: Int,
    @ColumnInfo(name = "isDone")
    val isDone: Boolean,
    @ColumnInfo(name = "deadline")
    val deadlineDate: Long?,
    @ColumnInfo(name = "modificationDate")
    val modificationDate: Long,
)
