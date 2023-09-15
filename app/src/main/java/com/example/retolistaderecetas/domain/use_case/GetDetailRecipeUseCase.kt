package com.example.retolistaderecetas.domain.use_case

import com.example.retolistaderecetas.data.Result
import com.example.retolistaderecetas.domain.model.Recipe
import com.example.retolistaderecetas.domain.repositories.RecipeRepository
import javax.inject.Inject

class GetDetailRecipeUseCase @Inject constructor(
    private val repository: RecipeRepository
) {
    suspend operator fun invoke(id: Int): Result<Recipe> {
        return repository.getDetailRecipe(id)
    }
}