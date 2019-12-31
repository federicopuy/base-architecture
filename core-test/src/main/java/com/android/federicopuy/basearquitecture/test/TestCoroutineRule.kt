package com.android.federicopuy.basearquitecture.test

import androidx.annotation.VisibleForTesting
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Delay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.resume


@VisibleForTesting
class TestCoroutineRule : TestRule {

    @InternalCoroutinesApi
    private val dispatcher = TestDispatcher()

    @InternalCoroutinesApi
    @ExperimentalCoroutinesApi
    override fun apply(base: Statement?, description: Description?) = object : Statement() {
        override fun evaluate() {
            try {
                Dispatchers.setMain(dispatcher)
                base?.evaluate()
            } finally {
                Dispatchers.resetMain()
            }
        }
    }
}

@InternalCoroutinesApi
private class TestDispatcher : CoroutineDispatcher(), Delay {
    override fun scheduleResumeAfterDelay(
        timeMillis: Long,
        continuation: CancellableContinuation<Unit>
    ) {
        continuation.resume(Unit)
    }

    override fun dispatch(context: CoroutineContext, block: Runnable) {
        block.run()
    }
}