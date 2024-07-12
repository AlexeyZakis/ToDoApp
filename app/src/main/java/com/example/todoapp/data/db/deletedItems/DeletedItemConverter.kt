package com.example.todoapp.data.db.deletedItems

import com.example.todoapp.data.db.models.DeletedItem

fun DeletedItemDb.toDeletedItem() = DeletedItem(
    id = id,
    date = date,
)

fun DeletedItem.toDeletedItemsDb() = DeletedItemDb(
    id = id,
    date = date,
)
