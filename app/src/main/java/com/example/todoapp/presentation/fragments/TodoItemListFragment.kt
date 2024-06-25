package com.example.todoapp.presentation.fragments

import android.graphics.Canvas
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentToDoItemListBinding
import com.example.todoapp.domain.models.Priority
import com.example.todoapp.domain.models.RecyclerItem
import com.example.todoapp.domain.models.TodoItemId
import com.example.todoapp.presentation.Constants.Constants
import com.example.todoapp.presentation.Constants.Mode
import com.example.todoapp.presentation.MainActivity
import com.example.todoapp.presentation.adapterDelegates.addTaskAdapterDelegate
import com.example.todoapp.presentation.adapterDelegates.todoItemAdapterDelegate
import com.example.todoapp.presentation.callbacks.AddTaskCallback
import com.example.todoapp.presentation.callbacks.ChangeCompletionCallback
import com.example.todoapp.presentation.callbacks.EditTodoItemCallback
import com.example.todoapp.presentation.functions.getColorFromAttr
import com.example.todoapp.presentation.viewModels.TodoItemEditViewModel
import com.example.todoapp.presentation.viewModels.TodoItemListViewModel
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator


class TodoItemListFragment :
    Fragment(),
    AddTaskCallback,
    ChangeCompletionCallback,
    EditTodoItemCallback
{
    private lateinit var binding: FragmentToDoItemListBinding
    private val todoItemListVM: TodoItemListViewModel by activityViewModels()
    private val todoItemEditVM: TodoItemEditViewModel by activityViewModels()
    private lateinit var taskAdapter: ListDelegationAdapter<List<Any>>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentToDoItemListBinding.inflate(inflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        todoItemListVM.getTodoItems()

        taskAdapter = ListDelegationAdapter(
            todoItemAdapterDelegate(this, this),
            addTaskAdapterDelegate(this),
        )
        binding.taskListRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = taskAdapter
            taskAdapter.items = (todoItemListVM.todoItems.value?.items ?: listOf()) +
                    RecyclerItem.AddTaskItem
        }
        todoItemListVM.todoItems.observe(viewLifecycleOwner) {
            onItemListUpdated()
        }
        todoItemListVM.hideDoneTasks.observe(viewLifecycleOwner) { hideDoneTasks ->
            todoItemListVM.getTodoItems()
            onItemListUpdated()
            changeHideDoneTasksIcon()
        }
        binding.hideDoneTaskCheckbox.setOnClickListener {
            todoItemListVM.hideDoneTasks.value =
                !(todoItemListVM.hideDoneTasks.value ?:
                Constants.HIDE_DONE_TASK_DEFAULT)
        }
        binding.addTaskBtn.setOnClickListener {
            onAddTask()
        }
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val swipedItem = viewHolder.itemView.tag as? RecyclerItem.TodoItem ?: return
                when (direction) {
                    ItemTouchHelper.LEFT -> {
                        todoItemListVM.deleteTodoItem(TodoItemId(swipedItem.id))
                    }
                    ItemTouchHelper.RIGHT -> {
                        swipedItem.isDone = !swipedItem.isDone
                    }
                }
                todoItemListVM.getTodoItems()
            }
            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                return if (viewHolder.itemView.tag == null) 0
                    else super.getMovementFlags(recyclerView, viewHolder)
            }

            override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
//                val swipedItem = viewHolder.itemView.tag as? RecyclerItem.TodoItem ?: return
//                val swipeRightIcon = if (swipedItem.isDone) R.drawable.baseline_close_24 else R.drawable.baseline_done_24
//                val swipeRightBackground = if (swipedItem.isDone) R.attr.colorGreen else R.attr.colorBlue
                RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(requireContext().getColorFromAttr(R.attr.colorRed))
                    .addSwipeLeftActionIcon(R.drawable.baseline_delete_24)
//                    .addSwipeRightBackgroundColor(requireContext().getColorFromAttr(swipeRightBackground))
//                    .addSwipeRightActionIcon(swipeRightIcon)
                    .addSwipeRightBackgroundColor(requireContext().getColorFromAttr(R.attr.colorGreen))
                    .addSwipeRightActionIcon(R.drawable.baseline_done_24)
                    .create()
                    .decorate()

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }
        }
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(binding.taskListRecycler)
    }

    override fun onEditTodoItem(todoItemId: TodoItemId) {
        val todoItem = todoItemListVM.getTodoItem(todoItemId)
        todoItemEditVM.apply {
            todoItemEditVM.todoItemId.value = todoItemId
            todoItemText.value = todoItem.taskText
            priority.value = todoItem.priority
            startDate.value = todoItem.startDate
            deadlineDate.value = todoItem.deadlineDate
            mode.value = Mode.EDIT_ITEM
        }
        openEditTodoItemFragment()
    }
    override fun onAddTask() {
        todoItemEditVM.apply {
            todoItemText.value = ""
            todoItemId.value = todoItemListVM.getAvailableTodoItemId()
            priority.value = Priority.NORMAL
            deadlineDate.value = null
            mode.value = Mode.ADD_ITEM
        }
        openEditTodoItemFragment()
    }
    override fun onChangeCompletion() {
        todoItemListVM.getTodoItems()
    }
    private fun openEditTodoItemFragment() {
        (requireActivity() as MainActivity).openFragment(TodoItemEditFragment.newInstance())
    }
    private fun onItemListUpdated() {
        val doneTaskCounterText =
            "${getString(R.string.doneTaskCounter)} - ${todoItemListVM.doneTaskCounter}"
        binding.doneTaskCounter.text = doneTaskCounterText

        todoItemListVM.setDiffUtilData(
            todoItemListVM.hideDoneTasks.value ?:
            Constants.HIDE_DONE_TASK_DEFAULT,
            taskAdapter
        )
    }
    private fun changeHideDoneTasksIcon() {
        if (todoItemListVM.hideDoneTasks.value ?: Constants.HIDE_DONE_TASK_DEFAULT)
            binding.hideDoneTaskCheckbox.setImageResource(R.drawable.baseline_visibility_off_24)
        else binding.hideDoneTaskCheckbox.setImageResource(R.drawable.baseline_visibility_24)
    }
    companion object {
        @JvmStatic
        fun newInstance() = TodoItemListFragment()
    }
}