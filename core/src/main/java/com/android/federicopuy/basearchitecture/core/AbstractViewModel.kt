package com.android.federicopuy.basearchitecture.core

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel


/**
 * Abstract ViewModel including mediator livedata to control the different states
 */
abstract class AbstractViewModel : ViewModel(), LifecycleObserver {

    internal val restoredList = mutableListOf<LiveData<*>>()

    internal val mediator = MediatorLiveData<ViewState>()
    
    internal fun restoreState() {
        launch {
            restoredList.mapNotNull { it.value }
                .filterIsInstance<ViewState>()
                .forEach { mediator.value = it }
        }
    }

    @VisibleForTesting
    fun observeForever(observer: Observer<ViewState>) = mediator.observeForever(observer)
}

/**
 * Creates a livedata object and adds it to the AbstractViewModel mediator
 */
fun <T : ViewState> AbstractViewModel.liveData(
    restored: Boolean = true
) = MutableLiveData<T>()
    .also { if (restored) restoredList.add(it) }
    .also {
        mediator.addSource(it) { value ->
            mediator.value = value
        }
    }


