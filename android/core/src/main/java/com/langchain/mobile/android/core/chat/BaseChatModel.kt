package com.langchain.mobile.android.core.chat

import com.langchain.mobile.android.core.base.message.BaseMessage
import com.langchain.mobile.android.core.base.model.BaseLanguageModel
import com.langchain.mobile.android.core.base.output.LLMOutputImpl
import com.langchain.mobile.android.core.base.output.LLMResult
import com.langchain.mobile.android.core.base.output.LLMResultImpl
import com.langchain.mobile.android.core.base.prompt.PromptValue

interface BaseChatModel : BaseLanguageModel {

    fun combineLLMOutputs(llmOutputs: List<LLMOutputImpl>): LLMOutputImpl? {
        return null
    }

    fun generate(messages: List<List<BaseMessage>>, stop: List<String> = listOf()): LLMResult {
        val results = messages.map { m -> generate(m, stop) }
        val llmOutput = combineLLMOutputs(results.map { res -> res.llmOutput })
        val generations = results.map { res -> res.generations }
        return LLMResultImpl(
            generations = generations,
            llmOutput = llmOutput
        )
    }

    override fun generatePrompt(prompts: List<PromptValue>, stop: List<String>): LLMResult {
        val promptMessages = prompts.map { p -> p.asMessages() }
//        val promptStrings = prompts.map { p -> p.asString() }
//        callbackManager.on_llm_start(
//            serialized = mapOf("name" to javaClass.name),
//            prompts = promptStrings,
//            verbose
//        )
        return kotlin.runCatching {
            val output = generate(promptMessages, stop)
//            callbackManager.on_llm_end(output, verbose)
            output
        }.onFailure {
//            callbackManager.on_llm_error(it, verbose)
        }.getOrThrow()
    }

    fun generate(messages: List<BaseMessage>, stop: List<String> = listOf()): ChatResult

}