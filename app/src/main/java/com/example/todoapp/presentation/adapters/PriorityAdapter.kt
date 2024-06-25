package com.example.todoapp.presentation.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getString
import com.example.todoapp.domain.models.Priority
import com.example.todoapp.presentation.Constants.Constants
import com.example.todoapp.presentation.Constants.Emoji
import com.example.todoapp.presentation.functions.getColorFromAttr

class PriorityAdapter(context: Context, priorities: List<Priority>) :
    ArrayAdapter<Priority>(context, android.R.layout.simple_spinner_item, priorities) {

    init {
        setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent) as TextView
        setItemText(view, getItem(position))
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getDropDownView(position, convertView, parent) as TextView
        setItemText(view, getItem(position))
        return view
    }

    private fun setItemText(view: TextView, priority: Priority?) {
        var text = ""
        when (priority) {
            Priority.HIGH -> {
                text += Constants.CRITICAL_PRIORITY_TASK_EMOJI
                view.setTextColor(context.getColorFromAttr(com.example.todoapp.R.attr.colorRed))
            }
            Priority.LOW -> {
                text += Constants.LOW_PRIORITY_TASK_EMOJI
                view.setTextColor(context.getColorFromAttr(com.example.todoapp.R.attr.labelPrimary))
            }
            else -> view.setTextColor(context.getColorFromAttr(com.example.todoapp.R.attr.labelPrimary))
        }
        text += priority?.let { priorityToText(it) }
        view.text = text
    }
    private fun priorityToText(priority: Priority): String {
        return when (priority) {
            Priority.HIGH -> getString(context, com.example.todoapp.R.string.priorityHigh)
            Priority.NORMAL -> getString(context, com.example.todoapp.R.string.priorityNormal)
            Priority.LOW -> getString(context, com.example.todoapp.R.string.priorityLow)
        }
    }
}