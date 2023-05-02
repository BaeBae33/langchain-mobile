package com.langchain.mobile.android.core.base.memory

import com.langchain.mobile.android.core.base.message.BaseMessage

interface BaseChatMessageHistory {
    val messages: List<BaseMessage>

    /**
     * Add a user message to the store
     */
    fun addUserMessage(message: String)

    /**
     * Add an AI message to the store
     */
    fun addAIMessage(message: String)

    fun clear()
}