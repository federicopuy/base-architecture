package com.android.federicopuy.basearchitecture.core

import androidx.lifecycle.LifecycleOwner

/**
 * Interface to implement a getViewModel structure in Activities or Fragments
 */
interface ViewModelInterface<T : AbstractViewModel> : LifecycleOwner {

    val viewModel: T

    fun onStateReceived(state: ViewState)
}
