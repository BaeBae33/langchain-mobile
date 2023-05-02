package com.langchain.mobile.android.core.base.model

import com.langchain.mobile.android.core.base.message.AIMessage
import com.langchain.mobile.android.core.base.message.BaseMessage
import com.langchain.mobile.android.core.base.message.ChatMessage
import com.langchain.mobile.android.core.base.message.HumanMessage
import com.langchain.mobile.android.core.base.message.SystemMessage
import com.langchain.mobile.android.core.base.output.LLMResult
import com.langchain.mobile.android.core.base.prompt.PromptValue


interface BaseLanguageModel {
    /**
     * Take in a list of prompt values and return an LLMResult.
     */
    fun generatePrompt(prompts: List<PromptValue>, stop: List<String> = arrayListOf()): LLMResult

    /**
     * Get the number of tokens present in the text.
     */
    fun getNumTokens(text: String): Int {
        // TODO: this method may not be exact.
        // TODO: this method may differ based on model (eg codex).

        // TODO: mobile, implement this
        return 0
    }

    /**
     * Get the number of tokens in the message.
     */
    fun getNumTokensFromMessages(messages: List<BaseMessage>): Int {
        return messages.sumOf { m -> getNumTokens(getBufferString(listOf(m))) }
    }

    /**
     * Get buffer string of messages.
     */
    private fun getBufferString(
        messages: List<BaseMessage>, humanPrefix: String = "Human", aiPrefix: String = "AI",
    ): String {
        return messages.joinToString("\n") { m ->
            val role = when (m) {
                is HumanMessage -> humanPrefix
                is AIMessage -> aiPrefix
                is SystemMessage -> "System"
                is ChatMessage -> m.role
                else -> throw Exception("Got unsupported message type:${m}}")
            }
            "${role}: ${m.content}"
        }
    }

}