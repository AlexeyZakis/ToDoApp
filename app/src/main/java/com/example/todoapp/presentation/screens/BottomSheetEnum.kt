package com.example.todoapp.presentation.screens

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todoapp.domain.models.Priority
import com.example.todoapp.presentation.themes.AppTheme
import com.example.todoapp.presentation.themes.mainTheme.MainTheme
import com.example.todoapp.presentation.themes.themeColors
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
    enumDefaultValue: T,
    animationColorMap: Map<T, Color>,
    colorAlpha: Float = 0.2f,
    animationDuration: Int,
    defaultBackgroundColor: Color
) {
    val scope = rememberCoroutineScope()

    var selectedEnum by remember { mutableStateOf(enumDefaultValue) }
    var backgroundColorState by remember { mutableStateOf(defaultBackgroundColor) }

    val backgroundColor by animateColorAsState(
        targetValue = backgroundColorState,
        animationSpec = tween(durationMillis = animationDuration)
    )

    LaunchedEffect(selectedEnum) {
        backgroundColorState = animationColorMap[selectedEnum]
            ?.copy(alpha = colorAlpha)
            ?: return@LaunchedEffect
        delay(animationDuration.toLong())
        backgroundColorState = defaultBackgroundColor
    }

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                enumValues.forEach { enumValue ->
                    Text(
                        text = stringResource(id = enumToStringResId(enumValue)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                selectedEnum = enumValue
                                onEnumSelected(enumValue)
                                scope.launch {
                                    sheetState.hide()
                                }
                            }
                            .padding(16.dp)
                    )
                }
            }
        },
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor),
        content = {}
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
        BottomSheetEnum(
            sheetState = sheetState,
            enumValues = Priority.entries.toTypedArray(),
            enumToStringResId = { priority -> priorityToRId(priority) },
            onEnumSelected = { _ ->  },
            enumDefaultValue = Priority.NORMAL,
            animationColorMap = mapOf(Priority.HIGH to themeColors.colorRed),
            animationDuration = 2000,
            defaultBackgroundColor = themeColors.colorWhite
        )
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Button(onClick = {
                scope.launch {
                    sheetState.show()
                }
            }) {
                Text("Show Bottom Sheet")
            }
        }
    }
}