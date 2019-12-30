package com.android.federicopuy.basearchitecture.core

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

/**
 * Make the ViewModel setups like observer lifecycle,
 * observer states and restore states.
 *
 * @param viewModelInterface provide properties for getViewModel setup
 */
internal fun setupViewModel(viewModelInterface: ViewModelInterface<*>) {
    with(viewModelInterface) {
        viewModel.apply {
            lifecycle.addObserver(this)
            mediator.value
            mediator.observe(this@with, Observer {
                it?.let(::onStateReceived)
            })
            restoreState()
        }
    }
}

inline fun <reified T : ViewModel> Fragment.getViewModel(noinline creator: (() -> T)? = null): Lazy<T> {
    return if (creator == null)
        lazy { ViewModelProviders.of(this).get(T::class.java) }
    else
        lazy { ViewModelProviders.of(this, BaseViewModelFactory(creator)).get(T::class.java) }
}

inline fun <reified T : ViewModel> AppCompatActivity.getViewModel(noinline creator: (() -> T)? = null): Lazy<T> {
    return if (creator == null)
        lazy { ViewModelProviders.of(this).get(T::class.java) }
    else
        lazy { ViewModelProviders.of(this, BaseViewModelFactory(creator)).get(T::class.java) }
}

class BaseViewModelFactory<T>(val creator: () -> T) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return creator() as T
    }
}


