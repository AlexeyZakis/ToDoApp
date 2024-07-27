package com.example.todoapp.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todoapp.domain.models.Priority
import com.example.todoapp.presentation.themes.AppTheme
import com.example.todoapp.presentation.themes.mainTheme.MainTheme
import com.example.todoapp.presentation.themes.themeColors
import com.example.todoapp.presentation.utils.getPriorityEmoji
import com.example.todoapp.presentation.utils.priorityToRId
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun <T> BottomSheet(
    sheetState: ModalBottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden),
    values: Array<T>,
    valueToStringResId: (T) -> Int,
    onSelect: (T) -> Unit,
    selected: T,
    valueColors: Map<T, Color> = mapOf(),
    valuePrefix: Map<T, String> = mapOf(),
    content: @Composable () -> Unit,
) {
    val scope = rememberCoroutineScope()

    ModalBottomSheetLayout(
        sheetState = sheetState,
        content = content,
        sheetContent = {
            Column(
                modifier = Modifier
                    .background(themeColors.backPrimary)
                    .padding(16.dp)
            ) {
                values.forEach { enumValue ->
                    val background = if (enumValue == selected) {
                        themeColors.backSecondary
                    } else {
                        themeColors.backPrimary
                    }
                    Text(
                        color = valueColors[enumValue] ?: themeColors.labelPrimary,
                        style = MaterialTheme.typography.titleLarge,
                        text = (valuePrefix[enumValue] ?: "") +
                                stringResource(id = valueToStringResId(enumValue)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onSelect(enumValue)
                                scope.launch {
                                    sheetState.hide()
                                }
                            }
                            .background(background)
                            .padding(16.dp)
                    )
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
private fun BottomSheetPreview() {
    AppTheme(theme = MainTheme) {
        val scope = rememberCoroutineScope()
        val sheetState: ModalBottomSheetState =
            rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

        var text by remember { mutableStateOf(Priority.NORMAL.name) }
        var selectedPriority by remember { mutableStateOf(Priority.NORMAL) }

        BottomSheet(
            sheetState = sheetState,
            values = Priority.entries.toTypedArray(),
            valueToStringResId = { priority -> priorityToRId(priority) },
            selected = selectedPriority,
            onSelect = { priority ->
                run {
                    selectedPriority = priority
                    text = priority.name
                }
            },
            valueColors = mapOf(
                Priority.HIGH to themeColors.colorRed
            ),
            valuePrefix = mapOf(
                Priority.LOW to getPriorityEmoji(Priority.LOW),
                Priority.HIGH to getPriorityEmoji(Priority.HIGH),
            )
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Button(onClick = {
                    scope.launch {
                        sheetState.show()
                    }
                }) {
                    Text(text)
                }
            }
        }
    }
}