package com.example.retolistaderecetas.domain.use_case

import com.example.retolistaderecetas.domain.model.Recipes
import com.example.retolistaderecetas.domain.repositories.RecipeRepository
import com.example.retolistaderecetas.util.Results
import javax.inject.Inject

class GetListRecipeUseCase @Inject constructor(
    private val repository: RecipeRepository
) {
    suspend operator fun invoke(page: Int): Results<List<Recipes>> {
        return repository.getListRecipe(page = page)
    }
}