package com.langchain.mobile.android.core.base.prompt

import com.langchain.mobile.android.core.base.message.BaseMessage
import com.langchain.mobile.android.core.base.message.HumanMessage

class StringPromptValue(private val text: String) : PromptValue {

    override fun asString(): String {
        return text
    }

    override fun asMessages(): List<BaseMessage> {
        return listOf(HumanMessage(text))
    }
}