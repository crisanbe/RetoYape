package com.example.retolistaderecetas.ui

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination

sealed class Screen(val route: String) {
    object Home: Screen("home")
    object Map : Screen("map?latitude={latitude}&longitude={longitude}") {
        fun createRoute(latitude: Double?, longitude: Double?): String {
            return "map?latitude=$latitude&longitude=$longitude"
        }
    }
    object Detail: Screen("detail?id={id}") {
        fun passId(id: Int): String {
            return "detail?id=$id"
        }
    }
}

class RecipeActions(navController: NavController) {
    val navigateToHome: () -> Unit = {
        navController.navigate(Screen.Home.route) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    val navigateToMapWithLocation = { latitude: Double?, longitude: Double? ->
        val route = Screen.Map.createRoute(latitude, longitude)
        navController.navigate(route) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
        }
    }

    val navigateToDetail = { id: Int ->
        navController.navigate(Screen.Detail.passId(id)) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
        }
    }
}






