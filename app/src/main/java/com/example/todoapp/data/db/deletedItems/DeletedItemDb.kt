package com.example.todoapp.data.db.deletedItems

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.todoapp.data.db.DbConstants

@Entity(tableName = DbConstants.DELETED_ITEMS_TABLE_NAME)
class DeletedItemDb(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "date")
    val date: Long,
)
