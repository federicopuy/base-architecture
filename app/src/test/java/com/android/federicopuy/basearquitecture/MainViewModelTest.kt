package com.android.federicopuy.basearquitecture

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.android.federicopuy.basearchitecture.core.ViewState
import com.android.federicopuy.basearchitecture.networking.ApiResult
import com.android.federicopuy.basearquitecture.model.Character
import com.android.federicopuy.basearquitecture.test.LifecycleOwnerTest
import com.android.federicopuy.basearquitecture.test.TestCoroutineRule
import com.android.federicopuy.basearquitecture.test.verify
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.spy
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.Mockito.`when` as whenever

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesRule = TestCoroutineRule()

    @Mock
    lateinit var observer: Observer<ViewState>

    private lateinit var viewModel: MainViewModel

    @Mock
    private lateinit var repository: MainRepository

    private val lifecycleOwner = LifecycleOwnerTest()

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        viewModel = spy(MainViewModel(repository))
        viewModel.observeForever(observer)
        lifecycleOwner.lifecycle.addObserver(viewModel)
    }

    @Test
    fun `when button is clicked, fetchCharacter() is called` () = runBlocking {
        viewModel.buttonClicked()
        verify(viewModel).fetchCharacter()
    }

    @Test
    fun `when fetchingCharacter call is succesfull , loading and text are shown`() = runBlocking {
        val character = Character("Luke")
        val successMock = ApiResult.Success(character)
        whenever(repository.getNextCharacter(1)).thenReturn(successMock)

        viewModel.fetchCharacter()
        observer.verify(Mockito.times(3)) {
            val (loadingStart, text, loadingEnd) = allValues
            Assert.assertEquals(MainState.Loading(true), loadingStart)
            Assert.assertEquals(MainState.ShowText("Luke"), text)
            Assert.assertEquals(MainState.Loading(false), loadingEnd)
        }
        Assert.assertEquals(2, viewModel.characterId)
    }

    @Test
    fun `when fetchingCharacter call fails , error is shown and loading is hidden`() = runBlocking {
        val apiErrorMock = ApiResult.Error.Network
        whenever(repository.getNextCharacter(1)).thenReturn(apiErrorMock)
        viewModel.fetchCharacter()
        observer.verify(Mockito.times(3)) {
            val (loadingStart, text, loadingEnd) = allValues
            Assert.assertEquals(MainState.Loading(true), loadingStart)
            Assert.assertEquals(MainState.ShowText("Error fetching Character"), text)
            Assert.assertEquals(MainState.Loading(false), loadingEnd)
        }
    }
}