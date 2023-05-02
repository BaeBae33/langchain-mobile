package com.langchain.mobile.android.core.base.memory

import com.langchain.mobile.android.core.base.message.AIMessage
import com.langchain.mobile.android.core.base.message.BaseMessage
import com.langchain.mobile.android.core.base.message.HumanMessage

class ChatMessageHistory : BaseChatMessageHistory {
    private val _messages = mutableListOf<BaseMessage>()

    override val messages: List<BaseMessage>
        get() = _messages

    override fun addUserMessage(message: String) {
        _messages.add(HumanMessage(message))
    }

    override fun addAIMessage(message: String) {
        _messages.add(AIMessage(message))
    }

    override fun clear() {
        _messages.clear()
    }
}