import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.retolistaderecetas.data.Result
import com.example.retolistaderecetas.data.source.remote.dto.Location
import com.example.retolistaderecetas.domain.model.Recipes
import com.example.retolistaderecetas.domain.use_case.GetListRecipeUseCase
import com.example.retolistaderecetas.domain.use_case.GetRecipesUseCase
import com.example.retolistaderecetas.domain.use_case.GetSearchRecipes
import com.example.retolistaderecetas.ui.home.HomeEvent
import com.example.retolistaderecetas.ui.home.HomeState
import com.example.retolistaderecetas.ui.home.HomeViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

@ExperimentalCoroutinesApi
class HomeViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @RelaxedMockK
    private lateinit var getRecipesUseCase: GetRecipesUseCase
    @RelaxedMockK
    private lateinit var getSearchRecipes: GetSearchRecipes
    @RelaxedMockK
    private lateinit var getListRecipeUseCase: GetListRecipeUseCase

    private lateinit var viewModel: HomeViewModel
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        MockKAnnotations.init(this)
        viewModel = HomeViewModel(getRecipesUseCase, getSearchRecipes, getListRecipeUseCase)
    }

    @Test
    fun `getListRecipe with increase false sets state correctly`() = runTest {
        // Configurar el caso de prueba
        val initialTotalPages = viewModel.totalPages

        // Simular el resultado de getListRecipeUseCase
        val mockRecipes: List<Recipes> = listOf(
            Recipes(
                id = 1,
                name = "test",
                location = Location("",""),
                image = ""
            )
        )
        coEvery { (getListRecipeUseCase.invoke(1)) } returns (flowOf(Result.Success(mockRecipes)))

        // Llamar al método a probar
        viewModel.getListRecipe(false)

        // Verificar que totalPages se mantiene igual y que state se actualiza correctamente
        val updatedTotalPages = viewModel.totalPages
        assertEquals(initialTotalPages, updatedTotalPages)

        // Verificar que state se actualiza correctamente
        val updatedState = viewModel.state
        assertEquals(mockRecipes, updatedState.recipes)
        assertFalse(updatedState.isLoading)
        assertTrue(updatedState.showNext)
    }

    @Test
    fun `resetSearchState resets state correctly`() {
        viewModel.resetSearchState()

        // Verificar que el estado se restablece correctamente
        assertEquals(ArrayList<Recipes>(), viewModel.state.recipes)
        assertEquals(1, viewModel.totalPages)
    }

    @Test
    fun `onEvent sets input correctly`() {
        // Configuración del caso de prueba
        val inputValue = "nueva receta"
        val event = HomeEvent.EnteredRecipe(inputValue)

        // Llama a la función a probar
        viewModel.onEvent(event)

        // Verifica que el estado se actualizó correctamente
        val updatedState = viewModel.state
        assertEquals(inputValue, updatedState.input)
    }

    @Test
    fun `onEvent does not change state for other event types`() {
        // Configuración del caso de prueba
        val initialInputValue = "valor inicial"
        viewModel.state = HomeState(input = initialInputValue)

        // Crea un evento diferente de HomeEvent.EnteredRecipe
        val event : HomeEvent = HomeEvent.EnteredRecipe("")
        when (event) {
            is HomeEvent.EnteredRecipe ->  viewModel.state =  viewModel.state.copy(input = event.value)
        }
        // Llama a la función a probar
        viewModel.onEvent(event)

        // Verifica que el estado no se haya modificado
        val updatedState = viewModel.state
        assertEquals(initialInputValue, updatedState.input)
    }

}
