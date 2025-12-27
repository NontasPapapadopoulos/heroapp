package com.plum.superheroapp.presentation

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Assert
import kotlin.collections.forEach
import kotlin.collections.isNotEmpty
import kotlin.math.max
import com.plum.superheroapp.presentation.BlocViewModel

/**
 * Add a variable number of events to the given view model and calls the supplied function
 * with the collected states that were emitted.
 */
@OptIn(ExperimentalCoroutinesApi::class)
suspend fun <E : Any, S, N> TestScope.onEvents(
    viewModel: BlocViewModel<E, S, N>,
    vararg events: E,
    collectedStatesCallback: (List<S>) -> Unit
) {
    val collectedStates = kotlin.collections.ArrayList<S>()
    val job = launch(UnconfinedTestDispatcher()) {
        viewModel.uiState.collect {
            collectedStates.add(it)
        }
    }

    testScheduler.advanceUntilIdle()

    if (events.isNotEmpty()) {
        collectedStates.clear()
        for (event in events) {
            viewModel.add(event)
            testScheduler.advanceUntilIdle()
        }
    }

    collectedStatesCallback(collectedStates)
    job.cancel()
}

/**
 * Calls the supplied function with the collected states that were emitted after the view model
 * was initialized. Use immediately after the view model is created.
 */
@OptIn(ExperimentalCoroutinesApi::class)
suspend fun <E : Any, S, N> TestScope.onInit(
    viewModel: BlocViewModel<E, S, N>,
    collectedStatesCallback: (List<S>) -> Unit
) = onEvents(viewModel, collectedStatesCallback = collectedStatesCallback)

fun <T> assertEqualsDebug(expected: List<T>, actual: List<T>) {
    (0..max(expected.size - 1, actual.size - 1)).forEach { index ->
        println("$index ${expected[index] == actual[index]}:\nexpected: ${expected[index]}\nactual:   ${actual[index]}\n\n")
        Assert.assertEquals(expected[index], actual[index])
    }
}