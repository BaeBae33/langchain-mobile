package com.langchain.mobile.android.core.chat

import com.langchain.mobile.android.core.base.output.LLMOutputImpl

/**
 * interface that contains all relevant information for a Chat Result.
 */
interface ChatResult {
    /**
     * List of the things generated.
     */
    val generations: List<ChatGeneration>

    /**
     * For arbitrary LLM provider specific output.
     */
    val llmOutput: LLMOutputImpl
}