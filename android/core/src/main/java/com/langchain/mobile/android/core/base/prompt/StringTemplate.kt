package com.langchain.mobile.android.core.base.prompt

/**
 * String prompt should expose the format method, returning a prompt
 */
interface StringPromptTemplate : BasePromptTemplate {

    /**
     * Create Chat Messages.
     */
    override fun formatPrompt(vararg pair: Pair<String, Any>): PromptValue {
        return StringPromptValue(format(*pair))
    }
}