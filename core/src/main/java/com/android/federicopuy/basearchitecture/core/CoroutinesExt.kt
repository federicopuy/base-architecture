package com.android.federicopuy.basearchitecture.core

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
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
fun AbstractViewModel.async(
    block: suspend CoroutineScope.() -> Unit
) = viewModelScope.async(start = CoroutineStart.LAZY, block = block)