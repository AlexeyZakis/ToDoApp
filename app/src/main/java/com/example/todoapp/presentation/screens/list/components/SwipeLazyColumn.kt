package com.example.todoapp.presentation.screens.list.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todoapp.presentation.themes.AppTheme
import com.example.todoapp.presentation.themes.mainTheme.MainTheme
import com.example.todoapp.presentation.themes.themeColors
import kotlinx.coroutines.delay


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> SwipeLazyColumn(
    item: T,
    animationDuration: Int = 500,
    onSwipeEndToStart: (T) -> Unit,
    onSwipeStartToEnd: (T) -> Unit,
    content: @Composable (T) -> Unit
) {
    var isEndToStartSwipe by remember { mutableStateOf(false) }
    var isStartToEndSwipe by remember { mutableStateOf(false) }

    val state = rememberSwipeToDismissBoxState(
        confirmValueChange = { value ->
            when (value) {
                SwipeToDismissBoxValue.EndToStart -> {
                    isEndToStartSwipe = true
                    true
                }
                SwipeToDismissBoxValue.StartToEnd -> {
                    isStartToEndSwipe = true
                    true
                }
                else -> {
                    false
                }
            }
        }
    )

    LaunchedEffect(key1 = isEndToStartSwipe) {
        if(isEndToStartSwipe) {
            delay(animationDuration.toLong())
            onSwipeEndToStart(item)
        }
    }
    LaunchedEffect(key1 = isStartToEndSwipe) {
        if(isStartToEndSwipe) {
            delay(animationDuration.toLong())
            onSwipeStartToEnd(item)
        }
    }
    AnimatedVisibility(
        visible = !isEndToStartSwipe && !isStartToEndSwipe,
        exit = shrinkVertically(
            animationSpec = tween(durationMillis = animationDuration),
            shrinkTowards = Alignment.Top
        ) + fadeOut()
    ) {
        SwipeToDismissBox(
            state = state,
            backgroundContent = {
                SwipeBackground(swipeToDismissBoxState = state)
            },
            content = { content(item) }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeBackground(
    swipeToDismissBoxState: SwipeToDismissBoxState
) {
    val color: Color
    val icon: ImageVector
    val contentAlignment: Alignment

    when (swipeToDismissBoxState.dismissDirection) {
        SwipeToDismissBoxValue.StartToEnd -> {
            color = themeColors.colorGreen
            icon = Icons.Default.Done
            contentAlignment = Alignment.CenterStart
        }
        SwipeToDismissBoxValue.EndToStart -> {
            color = themeColors.colorRed
            icon = Icons.Default.Delete
            contentAlignment = Alignment.CenterEnd
        }
        SwipeToDismissBoxValue.Settled -> {
            return
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color)
            .padding(16.dp),
        contentAlignment = contentAlignment
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.White
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SwipeLazyColumnPreview() {
    AppTheme(theme = MainTheme) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = themeColors.backPrimary
        ) {
            val programmingLanguages = remember {
                mutableStateListOf(
                    "Kotlin",
                    "Java",
                    "C++",
                    "C#",
                    "JavaScript",
                )
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                items(items = programmingLanguages, key = { it }) { language ->
                    SwipeLazyColumn(
                        item = language,
                        onSwipeEndToStart = {},
                        onSwipeStartToEnd = {}
                    ) { language ->
                        Text(
                            text = language,
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(themeColors.backSecondary)
                                .padding(16.dp)
                        )
                    }
                }
            }
        }
    }
}