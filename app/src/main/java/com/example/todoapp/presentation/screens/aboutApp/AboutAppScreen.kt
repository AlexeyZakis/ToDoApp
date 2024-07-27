package com.example.todoapp.presentation.screens.aboutApp

import android.view.ContextThemeWrapper
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.example.todoapp.R
import com.example.todoapp.presentation.screens.aboutApp.DivHandler.createDivConfiguration
import com.example.todoapp.presentation.themes.AppTheme
import com.example.todoapp.presentation.themes.mainTheme.MainTheme
import com.example.todoapp.presentation.themes.themeColors
import com.yandex.div.core.Div2Context

@Composable
fun AboutAppScreen(
    navigateBack: () -> Unit
) {
    Scaffold(
        containerColor = themeColors.backPrimary
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .background(themeColors.backPrimary)
                .padding(paddingValues)
        ) {
            AndroidView(
                factory = { context ->
                    val divJson = AssetReader.loadJSONFromAsset(
                        context,
                        "aboutApp.json"
                    )
                    val cardJson = divJson.getJSONObject("card")

                    val divContext = Div2Context(
                        baseContext = ContextThemeWrapper(context, R.style.Theme_ToDoApp),
                        configuration = createDivConfiguration(context, navigateBack)
                    )
                    val divView = Div2ViewFactory(divContext).createView(cardJson)
                    divView
                },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Preview
@Composable
private fun AboutAppScreenPreview() {
    AppTheme(theme = MainTheme, darkTheme = false) {
        AboutAppScreen(
            navigateBack = {}
        )
    }
}
