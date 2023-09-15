package com.example.retolistaderecetas.ui.detail

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retolistaderecetas.data.Result
import com.example.retolistaderecetas.domain.use_case.GetDetailRecipeUseCase
import com.example.retolistaderecetas.util.ID_PARAMETER
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getCharacterUseCase: GetDetailRecipeUseCase,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    var state by mutableStateOf(DetailState())
        private set

    init {
        getCharacter()
    }

    private fun getCharacter() {
        savedStateHandle.get<Int>(ID_PARAMETER)?.let { characterId ->
            viewModelScope.launch {
                getCharacterUseCase(characterId).also { result ->
                    when (result) {
                        is Result.Success -> {
                            state = state.copy(
                                recipe = result.data,
                                isLoading = false
                            )
                        }
                        is Result.Error -> {
                            state = state.copy(
                                isLoading = false
                            )
                        }
                        is Result.Loading -> {
                            state = state.copy(
                                isLoading = true
                            )
                        }
                    }
                }
            }
        }
    }
}