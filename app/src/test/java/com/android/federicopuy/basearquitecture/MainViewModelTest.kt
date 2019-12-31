package com.android.federicopuy.basearquitecture

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.android.federicopuy.basearchitecture.core.ViewState
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
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.Spy
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesRule = TestCoroutineRule()

    @Mock
    lateinit var observer: Observer<ViewState>

    @Spy
    private lateinit var viewModel: MainViewModel

    private val lifecycleOwner = LifecycleOwnerTest()

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        viewModel.observeForever(observer)
        lifecycleOwner.lifecycle.addObserver(viewModel)
    }

    @Test
    fun `when button is clicked, updateText() is called` () = runBlocking {
        viewModel.buttonClicked()
        verify(viewModel).updateText()
    }

    @Test
    fun `when updating text, loading and text are shown`() = runBlocking {
        viewModel.updateText()
        observer.verify(Mockito.times(3)) {
            val (loadingStart, text, loadingEnd) = allValues
            Assert.assertEquals(MainState.Loading(true), loadingStart)
            Assert.assertEquals(MainState.ShowText("Hello World"), text)
            Assert.assertEquals(MainState.Loading(false), loadingEnd)
        }
    }

}