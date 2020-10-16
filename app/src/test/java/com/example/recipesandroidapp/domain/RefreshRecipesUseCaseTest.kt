package com.example.recipesandroidapp.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.recipesandroidapp.entities.InAppRecipe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.ResponseBody
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class RefreshRecipesUseCaseTest {
    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    private lateinit var states: MutableLiveData<RefreshRecipesState>
    private lateinit var refreshRecipesUseCase: RefreshRecipesUseCase
    private lateinit var repository: RecipesRepository
    private lateinit var recipesLiveData: LiveData<List<InAppRecipe>>

    @Before
    fun initialize() {
        states = MutableLiveData<RefreshRecipesState>()
        repository = FakeRecipesRepository()
        refreshRecipesUseCase = RefreshRecipesUseCase(repository)
        recipesLiveData = repository.getRecipes()
    }

    @Test
    fun `in normal conditions when invoke assert success state, recipesList is not empty, recipes of type InAppRecipe `() = runBlockingTest {
        //when invoke
        refreshRecipesUseCase.invoke(true, states)

        //assert success state, recipesList is not empty, recipes of type InAppRecipe
        assert(!(recipesLiveData.value ?: listOf()).isEmpty())
        assert(recipesLiveData.value?.get(0) is InAppRecipe)
        assertThat(states.value, `is`(SuccessState))
    }

    @Test
    fun `when invoked and not connected assert that recipesList is null or empty, states value is null`() = runBlockingTest {
        //when invoked and not connected
        refreshRecipesUseCase.invoke(false, states)

        //assert that recipesList is null or empty, states value is null
        assert(recipesLiveData.value.isNullOrEmpty())
        assertThat(states.value, `is`(nullValue()))
    }

    @Test
    fun `when invoked and isLoading assert that recipesList is null or empty, states value is Loading`() = runBlockingTest {
        //when invoked and is loading
        states.value = LoadingState
        refreshRecipesUseCase.invoke(false, states)

        //assert that recipesList is null or empty, states value is null
        assert(recipesLiveData.value.isNullOrEmpty())
        assertThat(states.value, `is`(LoadingState))
    }

    @Test
    fun `when invoked and with bad response assert that recipesList is null or empty, states value is Loading`()= runBlockingTest {
        //arrange
        repository = FakeRecipesRepository(Response.error(404, ResponseBody.create(null,"")))
        refreshRecipesUseCase = RefreshRecipesUseCase(repository)
        recipesLiveData = repository.getRecipes()

        //when invoked and with bad response
        refreshRecipesUseCase.invoke(true, states)

        //assert that recipesList is null or empty, states value is Loading
        assert(recipesLiveData.value.isNullOrEmpty())
        assert(states.value is ErrorState)
    }
}