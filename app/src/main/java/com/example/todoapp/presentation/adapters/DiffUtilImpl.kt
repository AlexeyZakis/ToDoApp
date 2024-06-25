package com.example.todoapp.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.todoapp.domain.models.RecyclerItem

class DiffUtilImpl(
    private val oldItems: List<RecyclerItem>,
    private val newItems: List<RecyclerItem>,
) : DiffUtil.Callback() {
    override fun getOldListSize() = oldItems.size
    override fun getNewListSize() = newItems.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems[oldItemPosition].id == newItems[newItemPosition].id
    }
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems[oldItemPosition] == newItems[newItemPosition]
    }
}