package com.example.todoapp.presentation.screens.aboutApp

import android.content.Context
import com.yandex.div.core.DivConfiguration
import com.yandex.div.picasso.PicassoDivImageLoader

object DivHandler {
    fun createDivConfiguration(
        context: Context,
        navigateBack: () -> Unit
    ): DivConfiguration {
        return DivConfiguration.Builder(PicassoDivImageLoader(context))
            .actionHandler(NavigateBackDivActionHandler(navigateBack))
            .visualErrorsEnabled(true)
            .build()
    }
}