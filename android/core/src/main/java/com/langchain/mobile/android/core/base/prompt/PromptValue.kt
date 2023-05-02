package com.langchain.mobile.android.core.base.prompt

import com.langchain.mobile.android.core.base.message.BaseMessage

interface PromptValue {
    /**
     * Return prompt as string.
     */
    fun asString(): String

    /**
     * Return prompt as messages.
     */
    fun asMessages(): List<BaseMessage>
}