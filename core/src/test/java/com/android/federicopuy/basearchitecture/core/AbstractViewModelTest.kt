package com.android.federicopuy.basearchitecture.core

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.OnLifecycleEvent
import com.android.federicopuy.basearquitecture.test.LifecycleOwnerTest
import com.android.federicopuy.basearquitecture.test.TestCoroutineRule
import com.android.federicopuy.basearquitecture.test.verify
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Spy
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesRule = TestCoroutineRule()

    @Mock
    lateinit var observer: Observer<ViewState>

    private val lifecycleOwnerTest = LifecycleOwnerTest()

    @Spy
    private lateinit var viewModel: MockViewModel

    @Before
    fun setUp() {
        lifecycleOwnerTest.lifecycle.addObserver(viewModel)
        viewModel.observeForever(observer)
    }

    @Test
    fun `when onCreate lifecycle event, view state is updated`() {
        lifecycleOwnerTest.onCreate()
        observer.verify(times(1)) {
            val (state) = allValues
            Assert.assertEquals(MockViewState.OnCreate, state)
        }
    }

    @Test
    fun `when foo is executed with delay, view state is updated`() = runBlocking {
        viewModel.foo(true)

        observer.verify(times(1)) {
            val (state) = allValues
            Assert.assertEquals(MockViewState.WithDelay, state)
        }
    }

    @Test
    fun `when foo is executed without delay, view state is updated`() = runBlocking {
        viewModel.foo(false)

        observer.verify(times(1)) {
            val (state) = allValues
            Assert.assertEquals(MockViewState.WithoutDelay, state)
        }
    }

    @Test
    fun `state is restored correctly`() {
        viewModel.fooRestored()
        observer.verify(times(2))
        viewModel.restoreState()

        // 2 old states + 1 restore live data
        observer.verify(times(3))
    }

}

class MockViewModel : AbstractViewModel() {

    private val liveData = liveData<MockViewState>()

    private val unRestoredLiveData = liveData<MockViewState>(false)

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    private fun create() {
        liveData.postValue(MockViewState.OnCreate)
    }

    fun foo(withDelay: Boolean) {
        launch {
            val state = if (withDelay) {
                delay(1000)
                MockViewState.WithDelay
            } else {
                MockViewState.WithoutDelay
            }
            liveData.postValue(state)
        }
    }

    fun fooRestored() {
        liveData.postValue(MockViewState.Restored)
        unRestoredLiveData.postValue(MockViewState.Restored)
    }
}

sealed class MockViewState : ViewState {
    object OnCreate : MockViewState()
    object WithDelay : MockViewState()
    object WithoutDelay : MockViewState()
    object Restored : MockViewState()
}
