package com.example.todoapp.presentation.screens

import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todoapp.domain.models.Priority
import com.example.todoapp.presentation.themes.AppTheme
import com.example.todoapp.presentation.themes.mainTheme.MainTheme
import com.example.todoapp.presentation.themes.themeColors
import com.example.todoapp.presentation.utils.getPriorityEmoji
import com.example.todoapp.presentation.utils.priorityToRId
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun <T : Enum<T>> BottomSheetEnum(
    sheetState: ModalBottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden),
    enumValues: Array<T>,
    enumToStringResId: (T) -> Int,
    onEnumSelected: (T) -> Unit,
    enumSelected: T,
    enumMapColors: Map<T, Color> = mapOf(),
    enumMapPrefix: Map<T, String> = mapOf(),
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
                enumValues.forEach { enumValue ->
                    val background = if (enumValue == enumSelected) {
                        themeColors.backSecondary
                    } else {
                        themeColors.backPrimary
                    }
                    Text(
                        color = enumMapColors[enumValue] ?:
                            themeColors.labelPrimary,
                        style = MaterialTheme.typography.titleLarge,
                        text = (enumMapPrefix[enumValue] ?: "") +
                            stringResource(id = enumToStringResId(enumValue)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onEnumSelected(enumValue)
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
private fun BottomSheetEnumPreview() {
    AppTheme(theme = MainTheme) {
        val scope = rememberCoroutineScope()
        val sheetState: ModalBottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

        var text by remember { mutableStateOf(Priority.NORMAL.name) }
        var selectedPriority by remember { mutableStateOf(Priority.NORMAL) }

        BottomSheetEnum(
            sheetState = sheetState,
            enumValues = Priority.entries.toTypedArray(),
            enumToStringResId = { priority -> priorityToRId(priority) },
            enumSelected = selectedPriority,
            onEnumSelected = { priority -> run {
                selectedPriority = priority
                text = priority.name
            } },
            enumMapColors = mapOf(
                Priority.HIGH to themeColors.colorRed
            ),
            enumMapPrefix = mapOf(
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