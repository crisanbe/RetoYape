package com.example.retolistaderecetas.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retolistaderecetas.R
import com.example.retolistaderecetas.domain.model.Recipes
import com.example.retolistaderecetas.domain.model.info.Info
import com.example.retolistaderecetas.domain.use_case.GetListRecipeUseCase
import com.example.retolistaderecetas.domain.use_case.GetRecipesUseCase
import com.example.retolistaderecetas.domain.use_case.GetSearchRecipes
import com.example.retolistaderecetas.util.ErrorEntity
import com.example.retolistaderecetas.util.Results
import com.example.retolistaderecetas.util.UI_PAGE_SIZE
import com.example.retolistaderecetas.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getRecipesUseCase: GetRecipesUseCase,
    private val getSearchRecipes: GetSearchRecipes,
    private val getListRecipeUseCase: GetListRecipeUseCase
): ViewModel() {

    private var searchJob: Job? = null
    var totalPages = 1
    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()
    var state by mutableStateOf(HomeState(isLoading = true))

    init {
        getListRecipe(increase = false)
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.EnteredRecipe -> state = state.copy(input = event.value)
        }
    }

    fun getListRecipe(increase: Boolean) {
        viewModelScope.launch {
            if (increase) totalPages++ else if (totalPages > 1) totalPages--
            val showPrevious = totalPages > 1
            val showNext = totalPages < 42
            getListRecipeUseCase(totalPages)
                .also { result ->
                when (result) {
                    is Results.Success -> {
                        state = state.copy(
                            recipes = result.data ?: emptyList(),
                            isLoading = false,
                            showPrevious = showPrevious,
                            showNext = showNext
                        )
                    }
                    is Results.Error -> {
                        state = state.copy(
                            isLoading = false
                        )
                        _eventFlow.emit(UIEvent.ShowSnackBar(
                            message = "Algo salio mal!"
                        ))
                    }

                    else -> {}
                }
            }
        }
    }

    fun searchRecipes(input: String, press: Boolean = false) {
        searchJob?.cancel()
        if (input.isBlank()) {
            getListRecipe(increase = false)
        } else {
            if (press) {
                resetSearchState()
                filterRecipe(input)
            } else {
                if ((totalPages > 1) && (state.page < totalPages)) {
                    state = state.copy(page = state.page + 1)
                    filterRecipe(input)
                }
            }
        }
    }

    fun filterRecipe(input: String) {
        searchJob = viewModelScope.launch {
            try {
                state = state.copy(isLoading = true)
                val result = getSearchRecipes(state.page, input)
                handleResult(result, input)
            } finally {
                state = state.copy(isLoading = false)
            }
        }
    }

    private suspend fun handleResult(result: Results<Info>, input: String) {
        when (result) {
            is Results.Error -> handleResultError(result.error, input)
            is Results.Success -> handleResultSuccess(result.data, input)
            else -> Unit
        }
    }

    private suspend fun handleResultError(error: ErrorEntity, input: String) {
        val errorMessage = when (error) {
            ErrorEntity.ApiError.NotFound ->
                "No se ha podido contactar con el servidor, compruebe su conexión a Internet"
            ErrorEntity.ApiError.UnKnown ->
                "¡Uy, algo salió mal!"
            ErrorEntity.InputError.NameError ->
                UiText.StringResource(R.string.recipe, 2).toString()
        }

        _eventFlow.emit(UIEvent.ShowSnackBar(message = errorMessage))

        if (error == ErrorEntity.ApiError.NotFound || error == ErrorEntity.ApiError.UnKnown) {
            loadRecipes(input)
        }
    }

    private suspend fun handleResultSuccess(data: Any?, input: String) {
        val pages = (data as? Info)?.pages ?: 41
        totalPages = pages
        getRecipesUseCase(input, UI_PAGE_SIZE, (UI_PAGE_SIZE * (state.page - 1))).collect{
            appendRecipes(it, state.recipes)
        }

    }

    private suspend fun loadRecipes(input: String) {
        getRecipesUseCase(input, UI_PAGE_SIZE, (UI_PAGE_SIZE * (state.page - 1))).collect{
            state = state.copy(
                recipes = it,
                isLoading = false
            )
        }
    }


    fun appendRecipes(newRecipes: List<Recipes>, oldRecipes: List<Recipes>) {
        val current = ArrayList(oldRecipes)
        current.addAll(newRecipes)
        state = state.copy(recipes = current, isLoading = false)
    }

    private fun resetSearchState() {
        state = state.copy(recipes = ArrayList(), page = 1)
        totalPages = 1
    }

    sealed class UIEvent {
        data class ShowSnackBar(val message: String): UIEvent()
    }
}
