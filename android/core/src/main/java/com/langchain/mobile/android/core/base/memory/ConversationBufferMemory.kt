package com.langchain.mobile.android.core.base.memory

import com.langchain.mobile.android.core.base.Variable
import com.langchain.mobile.android.core.base.model.getBufferString

class ConversationBufferMemory(
    private val memoryKey: String = "history",
) : BaseChatMemory() {

    val buffer: String
        get() = getBufferString(chatMemory.messages)

    override fun loadMemoryVariables(vararg variable: Variable): List<Variable> {
        return listOf(Variable(memoryKey, buffer))
    }

    override fun memoryVariables(): List<String> {
        return listOf(memoryKey)
    }
}