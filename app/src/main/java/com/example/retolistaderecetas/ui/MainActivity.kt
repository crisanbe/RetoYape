package com.example.retolistaderecetas.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.retolistaderecetas.ui.theme.CORVETTE800
import com.example.retolistaderecetas.ui.theme.RecipeTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = rememberSystemUiController()
            val darkMode = remember { mutableStateOf(false) }
            SideEffect {
                systemUiController.setStatusBarColor(
                    color = CORVETTE800
                )
            }
            RecipeTheme(darkTheme = darkMode.value) {
                RecipesApp(darkMode)
            }
        }
    }
}