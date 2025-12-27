package com.plum.superheroapp.presentation

import org.mockito.internal.util.KotlinInlineClassUtil
import org.mockito.invocation.InvocationOnMock
import org.mockito.stubbing.Answer

class InlineClassesAnswer<T: Any>(private val defaultAnswer: Answer<T>): Answer<T> {

    @Suppress("UNCHECKED_CAST")
    override fun answer(invocation: InvocationOnMock?): T {
        return KotlinInlineClassUtil.unboxUnderlyingValueIfNeeded(
            invocation,
            defaultAnswer.answer(invocation)
        ) as T
    }

}