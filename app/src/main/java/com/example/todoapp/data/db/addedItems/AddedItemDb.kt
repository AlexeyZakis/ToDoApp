package com.example.todoapp.data.db.addedItems

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.todoapp.data.db.DbConstants

@Entity(tableName = DbConstants.ADDED_ITEMS_TABLE_NAME)
class AddedItemDb(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "date")
    val date: Long,
)
