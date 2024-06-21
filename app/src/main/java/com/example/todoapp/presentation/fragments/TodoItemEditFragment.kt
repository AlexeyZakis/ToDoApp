package com.example.todoapp.presentation.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import com.example.todoapp.databinding.FragmentToDoItemEditBinding
import com.example.todoapp.domain.models.Priority
import com.example.todoapp.domain.models.RecyclerItem
import com.example.todoapp.domain.models.TodoItemParams
import com.example.todoapp.presentation.MainActivity
import com.example.todoapp.presentation.Constants.Mode
import com.example.todoapp.presentation.adapters.PriorityAdapter
import com.example.todoapp.presentation.functions.DateFormat
import com.example.todoapp.presentation.functions.getColorFromAttr
import com.example.todoapp.presentation.functions.getDrawableWithColor
import com.example.todoapp.presentation.viewModels.TodoItemEditViewModel
import com.example.todoapp.presentation.viewModels.TodoItemListViewModel
import java.time.LocalDate
import java.util.Calendar

class TodoItemEditFragment : Fragment() {
    private lateinit var binding: FragmentToDoItemEditBinding
    private val todoItemListVM: TodoItemListViewModel by activityViewModels()
    private val todoItemEditVM: TodoItemEditViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentToDoItemEditBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val priorities = Priority.entries.toList()

        binding.deleteTaskIcon.setImageDrawable(requireContext().getDrawableWithColor(
            com.example.todoapp.R.drawable.baseline_delete_24,
            com.example.todoapp.R.attr.colorRed
        ))
        binding.hasDeadlineSwitch.setOnClickListener {
            binding.deadlineDate.visibility =
                if (binding.deadlineDate.visibility == View.INVISIBLE)
                    View.VISIBLE
                else
                    View.INVISIBLE
        }
        binding.taskPrioritySpinner.apply {
            adapter = PriorityAdapter(requireContext(), priorities)
            onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    todoItemEditVM.priority.value = priorities[position]
                }
                override fun onNothingSelected(p0: AdapterView<*>?) {}
            }
        }
        binding.deadlineLayout.setOnClickListener {
            if (!binding.hasDeadlineSwitch.isChecked)
                return@setOnClickListener
            showDatePickerDialog { year, month, day ->
                todoItemEditVM.deadlineDate.value = LocalDate.of(year, month + 1, day)
            }
        }
        binding.saveTaskBtn.setOnClickListener {
            todoItemEditVM.todoItemText.value = binding.taskText.text.toString()
            todoItemEditVM.lastChangeDate.value = LocalDate.now()
            todoItemEditVM.deadlineDate.value =
                if (!binding.hasDeadlineSwitch.isChecked) null
                else todoItemEditVM.deadlineDate.value ?: LocalDate.now()

            when (todoItemEditVM.mode.value) {
                Mode.ADD_ITEM -> addItem()
                Mode.EDIT_ITEM -> editItem()
                else -> addItem()
            }
            openTodoItemListFragment()
        }
        binding.closeBtn.setOnClickListener {
            openTodoItemListFragment()
        }
        binding.deleteTaskBtn.setOnClickListener {
            todoItemEditVM.todoItemId.value?.let { id ->
                todoItemListVM.deleteTodoItem(id)
            }
            openTodoItemListFragment()
        }
        todoItemEditVM.todoItemText.observe(viewLifecycleOwner) { todoItemText ->
            binding.taskText.setText(todoItemText)
        }
        todoItemEditVM.deadlineDate.observe(viewLifecycleOwner) { deadlineData ->
            deadlineData?.let {
                binding.deadlineDate.visibility = View.VISIBLE
                binding.hasDeadlineSwitch.isChecked = true
            }
            binding.deadlineDate.text = DateFormat.getDateString(
                deadlineData ?: LocalDate.now()
            )
        }
        todoItemEditVM.priority.observe(viewLifecycleOwner) { priority ->
            binding.taskPrioritySpinner.setSelection(priority.ordinal)
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    openTodoItemListFragment()
                }
            }
        )
    }
    private fun addItem() {
        todoItemEditVM.startDate.value = LocalDate.now()
        todoItemListVM.addTodoItem(RecyclerItem.TodoItem(
            id = todoItemEditVM.todoItemId.value?.value ?: todoItemListVM.getAvailableTodoItemId().value,
            taskText = todoItemEditVM.todoItemText.value.toString(),
            priority = todoItemEditVM.priority.value ?: Priority.NORMAL,
            startDate = todoItemEditVM.startDate.value ?: LocalDate.now(),
            isDone = false,
            deadlineDate = todoItemEditVM.deadlineDate.value,
            lastChangeDate = todoItemEditVM.lastChangeDate.value
        ))
    }
    private fun editItem() {
        todoItemListVM.editTodoItem(
            todoItemEditVM.todoItemId.value ?: todoItemListVM.getAvailableTodoItemId(),
            TodoItemParams(
                newTaskText = todoItemEditVM.todoItemText.value.toString(),
                newPriority = todoItemEditVM.priority.value,
                newDeadlineDate = todoItemEditVM.deadlineDate.value
            )
        )
    }
    private fun showDatePickerDialog(onDateSet: (year: Int, month: Int, dayOfMonth: Int) -> Unit) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                onDateSet(selectedYear, selectedMonth, selectedDayOfMonth)
            },
            year, month, dayOfMonth
        )
        datePickerDialog.datePicker.minDate = System.currentTimeMillis()
        datePickerDialog.show()
    }
    private fun openTodoItemListFragment() {
        (requireActivity() as MainActivity).openFragment(TodoItemListFragment.newInstance())
    }
    companion object {
        @JvmStatic
        fun newInstance() = TodoItemEditFragment()
    }
}