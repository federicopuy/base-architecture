package com.android.federicopuy.basearchitecture.core

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
 * Extension to launch coroutines scope
 */
fun AbstractViewModel.launch(
    block: suspend CoroutineScope.() -> Unit
) = viewModelScope.launch(block = block)

/**
 * Extension for async coroutines scope
 */
fun <T> AbstractViewModel.async(
    block: suspend CoroutineScope.() -> T): Deferred<T> = viewModelScope.async( block = block)