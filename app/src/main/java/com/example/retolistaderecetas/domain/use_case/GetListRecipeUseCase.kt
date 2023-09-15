package com.example.retolistaderecetas.domain.use_case

import com.example.retolistaderecetas.data.Result
import com.example.retolistaderecetas.domain.model.Recipes
import com.example.retolistaderecetas.domain.repositories.RecipeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetListRecipeUseCase @Inject constructor(
    private val repository: RecipeRepository
) {
    operator fun invoke(page: Int): Flow<Result<List<Recipes>>> {
        return repository.getListRecipe(page = page)
    }
}