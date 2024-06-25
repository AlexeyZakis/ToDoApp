package com.example.todoapp.presentation.adapterDelegates

import com.example.todoapp.databinding.AddTaskItemBinding
import com.example.todoapp.domain.models.RecyclerItem
import com.example.todoapp.presentation.callbacks.AddTaskCallback
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

fun addTaskAdapterDelegate(addTaskCallback: AddTaskCallback) =
    adapterDelegateViewBinding<RecyclerItem.AddTaskItem, Any, AddTaskItemBinding>(
        { layoutInflater, root -> AddTaskItemBinding.inflate(layoutInflater, root, false) }
    ) {
        bind {
            binding.addTaskText.setOnClickListener {
                addTaskCallback.onAddTask()
            }
        }
    }