package com.example.todoapp.presentation.adapterDelegates

import android.R
import android.content.res.ColorStateList
import android.graphics.Paint
import android.view.View
import androidx.core.widget.CompoundButtonCompat
import com.example.todoapp.databinding.TodoItemBinding
import com.example.todoapp.domain.models.Priority
import com.example.todoapp.domain.models.RecyclerItem
import com.example.todoapp.domain.models.TodoItemId
import com.example.todoapp.presentation.Constants.Constants
import com.example.todoapp.presentation.callbacks.ChangeCompletionCallback
import com.example.todoapp.presentation.callbacks.EditTodoItemCallback
import com.example.todoapp.presentation.functions.DateFormat
import com.example.todoapp.presentation.functions.getColorFromAttr
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

fun todoItemAdapterDelegate(
    changeCompletionCallback: ChangeCompletionCallback,
    editTodoItemCallback: EditTodoItemCallback,
) =
    adapterDelegateViewBinding<RecyclerItem.TodoItem, Any, TodoItemBinding>(
        { layoutInflater, root -> TodoItemBinding.inflate(layoutInflater, root, false) }
    ) {
        val states = arrayOf(
            intArrayOf(R.attr.state_checked),
            intArrayOf(-R.attr.state_checked)
        )
        val doneTaskColor = context.getColorFromAttr(com.example.todoapp.R.attr.colorGreen)
        var undoneTaskColor: Int
        var taskText: String

        bind {
            itemView.tag = item
            taskText = ""
            undoneTaskColor = context.getColorFromAttr(com.example.todoapp.R.attr.supportSeparator)

            binding.taskDeadline.text = item.deadlineDate?.let {
                DateFormat.getDateString(item.deadlineDate)
            } ?: ""

            when (item.priority) {
                Priority.HIGH -> {
                    undoneTaskColor = context.getColorFromAttr(com.example.todoapp.R.attr.colorRed)
                    taskText += Constants.CRITICAL_PRIORITY_TASK_EMOJI
                }
                Priority.LOW -> {
                    taskText += Constants.LOW_PRIORITY_TASK_EMOJI
                }
                else -> {}
            }
            taskText += item.taskText

            binding.taskText.text = taskText
            binding.isDoneCheckBox.isChecked = item.isDone

            if (item.deadlineDate == null || item.isDone) {
                binding.taskDeadline.visibility = View.GONE
            }

            if (item.isDone) {
                binding.taskText.setTextColor(context.getColorFromAttr(com.example.todoapp.R.attr.labelTertiary))
                binding.taskText.paintFlags = binding.taskText.paintFlags or
                        Paint.STRIKE_THRU_TEXT_FLAG
            }
            else {
                binding.taskText.setTextColor(context.getColorFromAttr(com.example.todoapp.R.attr.labelPrimary))
                binding.taskText.paintFlags = binding.taskText.paintFlags and
                        Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }

            val colors = intArrayOf(doneTaskColor, undoneTaskColor)
            val colorStateList = ColorStateList(states, colors)
            CompoundButtonCompat.setButtonTintList(binding.isDoneCheckBox, colorStateList)

            binding.isDoneCheckBox.setOnClickListener {
                item.isDone = binding.isDoneCheckBox.isChecked
                changeCompletionCallback.onChangeCompletion()
            }
            binding.todoItemLayout.setOnClickListener {
                editTodoItemCallback.onEditTodoItem(TodoItemId(item.id))
            }
        }
    }