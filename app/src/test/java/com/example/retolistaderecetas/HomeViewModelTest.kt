import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.retolistaderecetas.data.Result
import com.example.retolistaderecetas.data.source.remote.dto.Location
import com.example.retolistaderecetas.domain.model.Recipes
import com.example.retolistaderecetas.domain.use_case.GetListRecipeUseCase
import com.example.retolistaderecetas.domain.use_case.GetRecipesUseCase
import com.example.retolistaderecetas.domain.use_case.GetSearchRecipes
import com.example.retolistaderecetas.ui.home.HomeViewModel
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
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
    fun `getListRecipe increases totalPages`() = runTest {
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
        Mockito.`when`(getListRecipeUseCase.invoke(1)).thenReturn(flowOf(Result.Success(mockRecipes)))

        // Llamar al método a probar
        viewModel.getListRecipe(true)

        // Verificar que totalPages se incrementa correctamente
        val updatedTotalPages = viewModel.totalPages
        assertEquals(initialTotalPages + 1, updatedTotalPages)
    }


    @Test
    fun `resetSearchState resets state correctly`() {
        viewModel.resetSearchState()

        // Verificar que el estado se restablece correctamente
        assertEquals(ArrayList<Recipes>(), viewModel.state.recipes)
        assertEquals(1, viewModel.totalPages)
    }
}
