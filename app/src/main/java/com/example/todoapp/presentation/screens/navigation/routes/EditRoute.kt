package com.example.todoapp.presentation.screens.navigation.routes

import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.todoapp.presentation.screens.navigation.Route

/**
 * Set of tools for navigating to edit screen
 **/
object EditRoute : Route {
    const val ID = "id"
    private const val SOURCE_NAME = "EditScreen"

    override val route: String = "$SOURCE_NAME/{$ID}"

    val arguments = listOf(
        navArgument(ID) {
            type = NavType.StringType
        }
    )

    fun navigateToEditItem(id: String = "") = "$SOURCE_NAME/$id"
}
