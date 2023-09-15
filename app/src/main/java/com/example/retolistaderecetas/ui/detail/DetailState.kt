package com.example.retolistaderecetas.ui.detail

import com.example.retolistaderecetas.domain.model.Recipe

data class DetailState(
    val recipe: Recipe? = null,
    val isLoading: Boolean = false
)