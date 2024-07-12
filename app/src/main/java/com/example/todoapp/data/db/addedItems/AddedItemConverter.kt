package com.example.todoapp.data.db.addedItems

import com.example.todoapp.data.db.models.AddedItem

fun AddedItemDb.toAddedItem() = AddedItem(
    id = id,
    date = date,
)

fun AddedItem.toAddedItemsDb() = AddedItemDb(
    id = id,
    date = date,
)
