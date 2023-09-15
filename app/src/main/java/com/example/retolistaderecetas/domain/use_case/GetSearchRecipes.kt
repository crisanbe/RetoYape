package com.example.retolistaderecetas.domain.use_case

import com.example.retolistaderecetas.domain.repositories.RecipeRepository
import com.example.retolistaderecetas.util.ErrorEntity
import com.example.retolistaderecetas.util.MIN_NAME_LENGTH
import com.example.retolistaderecetas.util.Results
import javax.inject.Inject

class GetSearchRecipes @Inject constructor(
    private val repository: RecipeRepository
) {
    suspend operator fun invoke(page: Int, input: String) =
        if (input.length < MIN_NAME_LENGTH ) {
            Results.Error(ErrorEntity.InputError.NameError)
        } else {
            repository.getSearchRecipes(page, input)
        }
}