package com.example.todoapp.presentation.screens.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.todoapp.presentation.screens.aboutApp.AboutAppScreen
import com.example.todoapp.presentation.screens.edit.EditScreen
import com.example.todoapp.presentation.screens.edit.EditViewModel
import com.example.todoapp.presentation.screens.list.ListScreen
import com.example.todoapp.presentation.screens.list.ListViewModel
import com.example.todoapp.presentation.screens.navigation.routes.AboutAppRoute
import com.example.todoapp.presentation.screens.navigation.routes.EditRoute
import com.example.todoapp.presentation.screens.navigation.routes.ListRoute

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = ListRoute.route
    ) {
        composable(route = ListRoute.route) {
            val listViewModel: ListViewModel = hiltViewModel()
            val listScreenState by listViewModel.screenState.collectAsState()
            ListScreen(
                screenState = listScreenState,
                screenAction = listViewModel::screenAction,
                navigateToAboutApp = { navController.navigate(AboutAppRoute.route) },
                navigateToNewItem = { navController.navigate(EditRoute.route) },
                navigateToEditItem = { id ->
                    navController.navigate(
                        EditRoute.navigateToEditItem(id)
                    )
                }
            )
        }
        composable(route = EditRoute.route, arguments = EditRoute.arguments) {
            val editViewModel: EditViewModel = hiltViewModel()
            val editScreenState by editViewModel.screenState.collectAsState()
            EditScreen(
                screenState = editScreenState,
                screenAction = editViewModel::screenAction,
                navigateBack = {
                    navController.navigate(ListRoute.route) {
                        popUpTo(ListRoute.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(route = AboutAppRoute.route) {
            AboutAppScreen(
                navigateBack = {
                    navController.navigate(ListRoute.route) {
                        popUpTo(ListRoute.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}
