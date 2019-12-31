package com.android.federicopuy.basearquitecture.test

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.Observer
import org.mockito.ArgumentCaptor
import org.mockito.Mockito
import org.mockito.verification.VerificationMode

@VisibleForTesting
inline fun <reified T> mock(): T = Mockito.mock(T::class.java)

@VisibleForTesting
inline fun <reified T> argumentCaptor(
    block: ArgumentCaptor<T>.() -> Unit
) = ArgumentCaptor.forClass(T::class.java).run(block)

@VisibleForTesting
inline fun <reified T> Observer<T>.verify(
    mode: VerificationMode = Mockito.times(1),
    block: ArgumentCaptor<T>.() -> Unit = {}
) {
    argumentCaptor<T> {
        Mockito.verify(this@verify, mode).onChanged(capture())
        block()
    }
}