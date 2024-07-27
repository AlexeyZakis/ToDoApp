package com.example.todoapp.presentation.screens.list.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.CustomAccessibilityAction
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.semantics.SemanticsPropertyReceiver
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.customActions
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todoapp.R
import com.example.todoapp.domain.models.Priority
import com.example.todoapp.domain.models.TodoItem
import com.example.todoapp.presentation.constants.Constants
import com.example.todoapp.presentation.themes.AppTheme
import com.example.todoapp.presentation.themes.mainTheme.MainTheme
import com.example.todoapp.presentation.themes.themeColors
import com.example.todoapp.presentation.utils.DateFormat

@Composable
fun ListTodoItem(
    modifier: Modifier = Modifier,
    todoItem: TodoItem,
    onCheckboxClick: () -> Unit,
    onItemClick: () -> Unit,
) {
    var taskTextColor: Color = themeColors.labelPrimary
    var taskTextDecorator: TextDecoration? = null

    if (todoItem.isDone) {
        taskTextColor = themeColors.labelTertiary
        taskTextDecorator = TextDecoration.LineThrough
    }

    val taskDoneDescription = stringResource(id = R.string.taskDoneDescription)
    val taskUndoneDescription = stringResource(id = R.string.taskUndoneDescription)
    val undoneTaskDeadlineDescription = stringResource(id = R.string.undoneTaskDeadlineDescription)

    val doneTaskCheckboxClickDescription = stringResource(id = R.string.doneTaskCheckboxClickDescription)
    val undoneTaskCheckboxClickDescription = stringResource(id = R.string.undoneTaskCheckboxClickDescription)

    Row(
        verticalAlignment = Alignment.Top,
        modifier = modifier
            .clickable(onClickLabel =
                stringResource(id = R.string.todoItemDoubleClickActionDescription)
            ) { onItemClick() }
            .clearAndSetSemantics {
                contentDescription = "${todoItem.taskText}. ${
                    if (todoItem.isDone) taskDoneDescription else "$taskUndoneDescription. ${
                        todoItem.deadlineDate?.let {
                            "$undoneTaskDeadlineDescription ${
                                DateFormat.getDateString(it)
                            }"
                        }
                    }"
                }."
                customActions = listOf(
                    CustomAccessibilityAction(label = if (todoItem.isDone) {
                            doneTaskCheckboxClickDescription
                        } else {
                            undoneTaskCheckboxClickDescription
                        }
                    ) {
                        onCheckboxClick()
                        true
                    },
                )
            }
    ) {
        Checkbox(
            checked = todoItem.isDone,
            onCheckedChange = { onCheckboxClick() },
            colors = CheckboxDefaults.colors(
                checkedColor = themeColors.colorGreen,
                uncheckedColor = if (todoItem.priority == Priority.HIGH) {
                    themeColors.colorRed
                } else {
                    themeColors.supportSeparator
                },
            ),
            modifier = Modifier
                .testTag("todoItemCheckBox")
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(top = 12.dp)
        ) {
            var taskText = when (todoItem.priority) {
                Priority.HIGH -> Constants.CRITICAL_PRIORITY_TASK_EMOJI
                Priority.LOW -> Constants.LOW_PRIORITY_TASK_EMOJI
                else -> ""
            }
            taskText += todoItem.taskText
            Text(
                text = taskText,
                fontSize = 16.sp,
                color = taskTextColor,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyMedium,
                textDecoration = taskTextDecorator,
            )
            if (todoItem.deadlineDate != null && !todoItem.isDone) {
                Text(
                    text = DateFormat.getDateString(todoItem.deadlineDate),
                    fontSize = 14.sp,
                    color = themeColors.labelTertiary,
                    style = MaterialTheme.typography.bodySmall,
                )
            }
        }
        Icon(
            imageVector = Icons.Outlined.Info,
            contentDescription = null,
            tint = themeColors.labelTertiary,
            modifier = Modifier.padding(top = 12.dp, end = 16.dp, start = 12.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ListTodoItemPreview() {
    AppTheme(theme = MainTheme) {
        ListTodoItem(
            todoItem = TodoItem(
                taskText = "3.1415926535 8979323846 2643383279 5028841971 6939937510 5820974944 5923078164 0628620899 8628034825 3421170679 8214808651 3282306647 0938446095 5058223172 5359408128 4811174502 8410270193 8521105559 6446229489 5493038196 4428810975 6659334461 2847564823 3786783165 2712019091 4564856692 3460348610 4543266482 1339360726 0249141273 7245870066 0631558817 4881520920 9628292540 9171536436 7892590360 0113305305 4882046652 1384146951 9415116094 3305727036 5759591953 0921861173 8193261179 3105118548 0744623799 6274956735 1885752724 8912279381 8301194912 9833673362 4406566430 8602139494 6395224737 1907021798 6094370277 0539217176 2931767523 8467481846 7669405132 0005681271 4526356082 7785771342 7577896091 7363717872 1468440901 2249534301 4654958537 1050792279 6892589235 4201995611 2129021960 8640344181 5981362977 4771309960 5187072113 4999999837 2978049951 0597317328 1609631859 5024459455 3469083026 4252230825 3344685035 2619311881 7101000313 7838752886 5875332083 8142061717 7669147303 5982534904 2875546873 1159562863 8823537875 9375195778 1857780532 1712268066 1300192787 6611195909 2164201989",
                deadlineDate = System.currentTimeMillis(),
                priority = Priority.HIGH
            ),
            onCheckboxClick = {},
            onItemClick = {}
        )
    }
}
