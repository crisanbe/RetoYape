package com.example.retolistaderecetas.ui.home

import com.example.retolistaderecetas.domain.model.Recipes

data class HomeState(
    val recipes: List<Recipes> = ArrayList(),
    val showPrevious: Boolean = false,
    val input: String = "",
    val page: Int = 1,
    val showNext: Boolean = false,
    val isLoading: Boolean = false
)