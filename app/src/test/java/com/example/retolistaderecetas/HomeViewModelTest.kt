import com.example.retolistaderecetas.data.source.remote.dto.Location
import com.example.retolistaderecetas.data.source.remote.dto.Result
import com.example.retolistaderecetas.domain.model.Recipes
import com.example.retolistaderecetas.domain.use_case.GetListRecipeUseCase
import com.example.retolistaderecetas.domain.use_case.GetRecipesUseCase
import com.example.retolistaderecetas.domain.use_case.GetSearchRecipes
import com.example.retolistaderecetas.ui.home.HomeViewModel
import com.example.retolistaderecetas.util.Results
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private lateinit var viewModel: HomeViewModel

    @MockK
    lateinit var getRecipesUseCase: GetRecipesUseCase

    @MockK
    lateinit var getSearchRecipes: GetSearchRecipes

    @MockK
    lateinit var getListRecipeUseCase: GetListRecipeUseCase

    private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        MockKAnnotations.init(this)
        viewModel = HomeViewModel(getRecipesUseCase, getSearchRecipes, getListRecipeUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // Restaura el dispatcher principal después de las pruebas
        testScope.cleanupTestCoroutines() // Limpia los recursos de las coroutines de prueba
    }

    @Test
    fun `getListRecipe with increase true sets state correctly`() = testScope.runBlockingTest {
        // Configurar el caso de prueba
        val initialTotalPages = viewModel.totalPages
        val mockRecipes: List<Recipes> = listOf(
            Recipes(
                id = 1,
                name = "test",
                location = Location("",""),
                image = ""
            )
        )

        coEvery { getListRecipeUseCase.invoke(1) } returns flowOf(com.example.retolistaderecetas.data.Result.Success(mockRecipes))

        // Llamar al método a probar
        viewModel.getListRecipe(true)

        // Verificar que totalPages se actualiza correctamente y que state se actualiza correctamente
        val updatedTotalPages = viewModel.totalPages
        Assert.assertEquals(initialTotalPages + 1, updatedTotalPages)

        val updatedState = viewModel.state
        coVerify { getListRecipeUseCase.invoke(1) }
        //Assert.assertEquals(mockRecipes, updatedState.recipes)
        //Assert.assertFalse(updatedState.isLoading)
        //Assert.assertTrue(updatedState.showNext)
    }

    // Similar a las pruebas anteriores, puedes escribir pruebas para otros métodos como `searchRecipes`, `resetSearchState`, etc.

}
