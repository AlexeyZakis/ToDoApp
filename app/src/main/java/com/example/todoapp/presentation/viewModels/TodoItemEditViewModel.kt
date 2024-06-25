package com.example.todoapp.presentation.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todoapp.domain.models.Priority
import com.example.todoapp.domain.models.TodoItemId
import com.example.todoapp.presentation.Constants.Mode
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class TodoItemEditViewModel @Inject constructor(): ViewModel() {
    var todoItemId = MutableLiveData<TodoItemId>()
    var todoItemText = MutableLiveData<String>()
    var priority = MutableLiveData<Priority>()
    var startDate = MutableLiveData<LocalDate>()
    var deadlineDate = MutableLiveData<LocalDate>()
    var lastChangeDate = MutableLiveData<LocalDate>()
    var mode = MutableLiveData<Mode>()
}