import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.retolistaderecetas.data.source.remote.dto.Location
import com.example.retolistaderecetas.domain.model.Recipes
import com.example.retolistaderecetas.domain.use_case.GetListRecipeUseCase
import com.example.retolistaderecetas.domain.use_case.GetRecipesUseCase
import com.example.retolistaderecetas.domain.use_case.GetSearchRecipes
import com.example.retolistaderecetas.ui.home.HomeEvent
import com.example.retolistaderecetas.ui.home.HomeState
import com.example.retolistaderecetas.ui.home.HomeViewModel
import com.example.retolistaderecetas.util.ErrorEntity
import com.example.retolistaderecetas.util.Results
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var viewModel: HomeViewModel

    @RelaxedMockK
    lateinit var getRecipesUseCase: GetRecipesUseCase

    @RelaxedMockK
    lateinit var getSearchRecipes: GetSearchRecipes

    @RelaxedMockK
    lateinit var getListRecipeUseCase: GetListRecipeUseCase

    private val testDispatcher = UnconfinedTestDispatcher()
    lateinit var mockRecipes: List<Recipes>

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        MockKAnnotations.init(this)
        viewModel = HomeViewModel(getRecipesUseCase, getSearchRecipes, getListRecipeUseCase)
        mockRecipes = listOf(
            Recipes(id = 1, name = "test", location = Location("", ""), image = "imagen")
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getListRecipe with increase true sets state correctly`(): Unit = runBlocking {
        val initialTotalPages = viewModel.totalPages

        coEvery { getListRecipeUseCase.invoke(1) } returns (Results.Success(mockRecipes))
        coEvery { getListRecipeUseCase.invoke(2) } returns (Results.Success(mockRecipes))

        viewModel.getListRecipe(true)

        val updatedTotalPages = viewModel.totalPages
        Assert.assertEquals(initialTotalPages + 1, updatedTotalPages)

        val updatedState = viewModel.state
        coVerify { getListRecipeUseCase.invoke(1) }
        coVerify { getListRecipeUseCase.invoke(2) }
        Assert.assertEquals(mockRecipes, updatedState.recipes)
        Assert.assertFalse(updatedState.isLoading)
        Assert.assertTrue(updatedState.showNext)
    }

    @Test
    fun `getListRecipe with increase false and totalPages mayor 1 sets state correctly`(): Unit =
        runBlocking {
            viewModel.totalPages = 2
            val initialTotalPages = viewModel.totalPages

            coEvery { getListRecipeUseCase.invoke(1) } returns (Results.Success(mockRecipes))

            viewModel.getListRecipe(false)

            val updatedTotalPages = viewModel.totalPages
            Assert.assertEquals(initialTotalPages - 1, updatedTotalPages)

            val updatedState = viewModel.state
            coVerify { getListRecipeUseCase.invoke(1) }
            Assert.assertEquals(mockRecipes, updatedState.recipes)
            Assert.assertFalse(updatedState.isLoading)
            Assert.assertTrue(updatedState.showNext)
        }

    @Test
    fun `getListRecipe with increase false and totalPages == 1 does not change state`(): Unit =
        runBlocking {
            viewModel.totalPages = 1
            val initialTotalPages = viewModel.totalPages

            val initialState = HomeState(isLoading = true)

            viewModel.getListRecipe(false)

            val updatedTotalPages = viewModel.totalPages
            Assert.assertEquals(initialTotalPages, updatedTotalPages)
            val updatedState = viewModel.state
            Assert.assertEquals(initialState, updatedState)
        }

    @Test
    fun `onEvent EnteredRecipe sets state correctly`() {
        val event = HomeEvent.EnteredRecipe("test value")
        viewModel.onEvent(event)
        val updatedState = viewModel.state
        Assert.assertEquals("test value", updatedState.input)
    }

    @Test
    fun `searchRecipes with empty input should call getListRecipe`(): Unit = runBlocking {
        coEvery { getRecipesUseCase.invoke("", eq(20), eq(0)) }
        val increase = false
        viewModel.searchRecipes("", increase)
        coVerify(exactly = 0) { getSearchRecipes(anyInt(), anyString()) }
    }


    @Test
    fun `searchRecipes with non-empty input and press=false should not reset search state`(): Unit =
        runBlocking {
            val input = "Test Input"
            val press = false
            viewModel.searchRecipes(input, press)
            coVerify(exactly = 0) { getRecipesUseCase(anyString(), anyInt(), anyInt()) }
        }

    @Test
    fun `searchRecipes with non-empty input and press=true should reset search state and call searchRecipes`(): Unit =
        runBlocking {
            val input = "Test Input"
            val press = true
            viewModel.searchRecipes(input, press)
            coVerify(exactly = 0) { getRecipesUseCase(anyString(), anyInt(), anyInt()) }
        }

    @Test
    fun `searchRecipes handles ApiError NotFound`() = runBlocking {
        val mockRecipes: List<Recipes> = emptyList()
        coEvery { getSearchRecipes(any(), any()) } returns Results.Error(ErrorEntity.ApiError.NotFound)
        coEvery { getRecipesUseCase(any(), any(), any()) } returns flowOf(mockRecipes)

        viewModel.filterRecipe("testInput")

        val updatedState = viewModel.state
        Assert.assertEquals(false, updatedState.isLoading)
        Assert.assertEquals(mockRecipes, updatedState.recipes)
    }

    @Test
    fun `searchRecipes handles Success`() = runBlocking {
        val mockRecipes = com.example.retolistaderecetas.domain.model.info.Info(1)
        coEvery { getSearchRecipes(any(), any()) } returns Results.Success(mockRecipes)

        viewModel.filterRecipe("testInput")

        val updatedState = viewModel.state
        Assert.assertEquals(false, updatedState.isLoading)
        Assert.assertEquals(mockRecipes, com.example.retolistaderecetas.domain.model.info.Info(updatedState.page)
        )
    }

    @Test
    fun `appendRecipes correctly updates state`() {
        val initialRecipes = listOf(
            Recipes(id = 1, name = "Recipe 1", location = Location("", ""), image = ""),
            Recipes(id = 2, name = "Recipe 2", location = Location("", ""), image = "")
        )

        val newRecipes = listOf(
            Recipes(id = 3, name = "Recipe 3", location = Location("", ""), image = ""),
            Recipes(id = 4, name = "Recipe 4", location = Location("", ""), image = "")
        )

        viewModel.appendRecipes(newRecipes, initialRecipes)

        val updatedState = viewModel.state
        val expectedRecipes = initialRecipes + newRecipes
        Assert.assertEquals(expectedRecipes, updatedState.recipes)
        Assert.assertFalse(updatedState.isLoading)
    }
}
