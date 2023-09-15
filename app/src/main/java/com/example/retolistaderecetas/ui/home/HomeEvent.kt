package com.example.retolistaderecetas.ui.home

sealed class HomeEvent {
    data class EnteredRecipe(val value: String): HomeEvent()
}
