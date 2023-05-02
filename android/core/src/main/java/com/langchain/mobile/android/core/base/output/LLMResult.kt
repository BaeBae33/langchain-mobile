package com.langchain.mobile.android.core.base.output

/**
 * interface that contains all relevant information for an LLM Result.
 */
interface LLMResult {
    /**
     * List of the things generated. This is List[List[]] because
     * each input could have multiple generations.
     */
    val generations: List<List<Generation>>

    /**
     * For arbitrary LLM provider specific output.
     */
    val llmOutput: LLMOutputImpl?
}