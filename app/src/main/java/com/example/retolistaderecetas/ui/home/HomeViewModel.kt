package com.example.retolistaderecetas.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retolistaderecetas.domain.model.Recipes
import com.example.retolistaderecetas.domain.use_case.GetRecipesUseCase
import com.example.retolistaderecetas.domain.use_case.GetSearchRecipes
import com.example.retolistaderecetas.util.ErrorEntity
import com.example.retolistaderecetas.util.Results
import com.example.retolistaderecetas.domain.use_case.GetListRecipeUseCase
import com.example.retolistaderecetas.util.UI_PAGE_SIZE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.ArrayList
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
                .distinctUntilChanged()
                .onEach { result ->
                when (result) {
                    is com.example.retolistaderecetas.data.Result.Success -> {
                        state = state.copy(
                            recipes = result.data ?: emptyList(),
                            isLoading = false,
                            showPrevious = showPrevious,
                            showNext = showNext
                        )
                    }
                    is com.example.retolistaderecetas.data.Result.Error -> {
                        state = state.copy(
                            isLoading = false
                        )
                        _eventFlow.emit(UIEvent.ShowSnackBar(
                            result.message ?: "Unknown error"
                        ))
                    }
                    is com.example.retolistaderecetas.data.Result.Loading -> {
                        state = state.copy(
                            isLoading = true
                        )
                    }
                }
            }.launchIn(this)
        }
    }

    fun searchRecipes(input: String, press: Boolean = false) {
        searchJob?.cancel()
        if (input.isBlank()) {
            // Si el campo de búsqueda está vacío, listar todas las recetas
            getListRecipe(increase = false)
        } else {
            if (press) {
                resetSearchState()
                searchRecipes(input)
            } else {
                if ((totalPages > 1) && (state.page < totalPages)) {
                    state = state.copy(page = state.page + 1)
                    searchRecipes(input)
                }
            }
        }
    }


    private fun searchRecipes(input: String) {
        searchJob = viewModelScope.launch {
            delay(1000L)
            state = state.copy(isLoading = true)
            getSearchRecipes(state.page, input).also { result ->
                when (result) {
                    is Results.Error -> {
                        when (result.error) {
                            ErrorEntity.ApiError.NotFound -> {
                                _eventFlow.emit(UIEvent.ShowSnackBar(
                                    message = "No se ha podido contactar con el servidor, compruebe su conexión a Internet"
                                ))
                                getRecipesUseCase(input, UI_PAGE_SIZE, (UI_PAGE_SIZE*(state.page-1))).collect {
                                    state = state.copy(
                                        recipes = it,
                                        isLoading = false
                                    )
                                }
                            }
                            ErrorEntity.ApiError.UnKnown -> {
                                _eventFlow.emit(UIEvent.ShowSnackBar(
                                    message = "¡Uy, algo salió mal!"
                                ))
                                getRecipesUseCase(input, UI_PAGE_SIZE, (UI_PAGE_SIZE*(state.page-1))).collect {
                                    state = state.copy(
                                        recipes = it,
                                        isLoading = false
                                    )
                                }
                            }
                            ErrorEntity.InputError.NameError -> {
                                _eventFlow.emit(UIEvent.ShowSnackBar(
                                    message = "La receta debe tener al menos %d caracteres"
                                ))
                            }
                        }
                    }
                    is Results.Success -> {
                        totalPages = result.data?.pages ?: 41
                        getRecipesUseCase(input, UI_PAGE_SIZE, (UI_PAGE_SIZE*(state.page-1))).collect {
                            appendRecipes(it, state.recipes)
                        }
                    }
                }
                state = state.copy(isLoading = false)
            }
        }
    }

    private fun appendRecipes(newRecipes: List<Recipes>, oldRecipes: List<Recipes>) {
        val current = ArrayList(oldRecipes)
        current.addAll(newRecipes)
        state = state.copy(recipes = current, isLoading = false)
    }

    fun resetSearchState() {
        state = state.copy(recipes = ArrayList(), page = 1)
        totalPages = 1
    }

    sealed class UIEvent {
        data class ShowSnackBar(val message: String): UIEvent()
    }
}
