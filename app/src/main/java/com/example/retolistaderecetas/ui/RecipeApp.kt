package com.example.retolistaderecetas.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import com.example.retolistaderecetas.ui.theme.RecipeTheme

@Composable
fun RecipesApp() {
    RecipeTheme {
        val navController = rememberNavController()
        val navigationActions = remember(navController) {
            RecipeActions(navController)
        }

        RecipeNavGraph(
            navController = navController,
            navigateToHome = navigationActions.navigateToHome,
            navigateToMap = navigationActions.navigateToMapWithLocation,
            navigateToDetail = navigationActions.navigateToDetail
        )
    }
}