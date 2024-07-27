package com.example.todoapp.presentation.screens.aboutApp

import android.net.Uri
import com.yandex.div.core.DivActionHandler
import com.yandex.div.core.DivViewFacade
import com.yandex.div.json.expressions.ExpressionResolver
import com.yandex.div2.DivAction

class NavigateBackDivActionHandler(private val navigateBack: () -> Unit) : DivActionHandler() {
    override fun handleAction(
        action: DivAction,
        view: DivViewFacade,
        resolver: ExpressionResolver
    ): Boolean {
        val url = action.url?.evaluate(resolver)
            ?: return super.handleAction(action, view, resolver)
        return if (url.scheme == SCHEME_SAMPLE && handleNavigateBackAction(url)) {
            true
        } else {
            super.handleAction(action, view, resolver)
        }
    }
    private fun handleNavigateBackAction(action: Uri): Boolean {
        return when (action.host) {
            "back" -> {
                navigateBack()
                true
            }
            else -> false
        }
    }

    companion object {
        const val SCHEME_SAMPLE = "my-action"
    }
}