package com.langchain.mobile.android.core.base.prompt

import com.langchain.mobile.android.core.base.Variable

/**
 * String prompt should expose the format method, returning a prompt
 */
interface StringPromptTemplate : BasePromptTemplate {

    /**
     * Create Chat Messages.
     */
    override fun formatPrompt(vararg variable: Variable): PromptValue {
        return StringPromptValue(format(*variable))
    }
}