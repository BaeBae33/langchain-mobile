package com.langchain.mobile.chatmodels

import com.langchain.mobile.android.core.base.prompt.PromptTemplate
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class PromptTemplateTest {
    @Test
    fun format_prompt() {

        assert(
            PromptTemplate("Hello {name}, how {blabla} you").format("name" to "World", "blabla" to "are") ==
                    "Hello World, how are you"
        )
    }
}