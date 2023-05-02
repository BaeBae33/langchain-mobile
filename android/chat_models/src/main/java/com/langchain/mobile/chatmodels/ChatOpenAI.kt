package com.langchain.mobile.chatmodels

import com.langchain.mobile.android.core.base.message.AIMessage
import com.langchain.mobile.android.core.base.message.BaseMessage
import com.langchain.mobile.android.core.base.message.ChatMessage
import com.langchain.mobile.android.core.base.message.HumanMessage
import com.langchain.mobile.android.core.base.message.SystemMessage
import com.langchain.mobile.android.core.base.output.LLMOutputImpl
import com.langchain.mobile.android.core.chat.BaseChatModel
import com.langchain.mobile.android.core.chat.ChatGenerationImpl
import com.langchain.mobile.android.core.chat.ChatResult
import com.langchain.mobile.android.core.chat.SimpleChatResultImpl
import com.unfbx.chatgpt.OpenAiClient
import com.unfbx.chatgpt.entity.chat.ChatChoice
import com.unfbx.chatgpt.entity.chat.ChatCompletion
import com.unfbx.chatgpt.entity.chat.ChatCompletionResponse
import com.unfbx.chatgpt.entity.chat.Message

class ChatOpenAI(private val openaiApiKey: String, initBlock: ChatOpenAI.() -> Unit = {}) :
    BaseChatModel {

    init {
        initBlock(this)
    }

    /**
     * Model name to use.
     */
    var modelName: String = "gpt-3.5-turbo"

    /**
     * What sampling temperature to use.
     */
    var temperature: Double = 0.7

    var openaiOrganization: String? = null

    /**
     * Timeout in seconds for the OpenAPI request.
     */
    val requestTimeout: Int = 60

    /**
     * Maximum number of retries to make when generating.
     */
    val maxRetries: Int = 6

    /**
     * Whether to stream the results or not.
     */
    val streaming: Boolean = false

    /**
     * Number of chat completions to generate for each prompt.
     */
    val n: Int = 1

    /**
     * Maximum number of tokens to generate.
     */
    val maxTokens: Int? = null

    /**
     * Use tenacity to retry the completion call.
     */
    private fun completionWithRetry(messages: List<BaseMessage>, stop: List<String>): ChatCompletionResponse {
        val openAiClient = OpenAiClient.Builder()
            .apiKey(listOf(openaiApiKey))
            .build()
        val messageList = messages.map { m ->
            when (m) {
                is HumanMessage -> Message.Builder().role(Message.Role.USER).content(m.content).build()
                is AIMessage -> Message.Builder().role(Message.Role.ASSISTANT).content(m.content).build()
                is SystemMessage -> Message.Builder().role(Message.Role.SYSTEM).content(m.content).build()
                else -> throw Exception("Got unknown type $m")
            }
        }
        val chatCompletion = ChatCompletion.builder()
            .messages(messageList)
            .model(modelName)
            .temperature(temperature)
            .n(n)
            .stream(streaming)
            .maxTokens(maxTokens)
            .apply {
                if (stop.isNotEmpty()) {
                    stop(stop)
                }
            }.build()
        return openAiClient.chatCompletion(chatCompletion)
    }

    override fun combineLLMOutputs(llmOutputs: List<LLMOutputImpl>): LLMOutputImpl? {
        return if (llmOutputs.isNotEmpty()) {
            null
        } else {
            LLMOutputImpl(llmOutputs.sumOf { it.tokenUsage }, modelName = llmOutputs.first().modelName)
        }
    }

    override fun generate(messages: List<BaseMessage>, stop: List<String>): ChatResult {
        val response = completionWithRetry(messages, stop)
        return createChatResult(response)
    }

    private fun createChatResult(chatCompletionResponse: ChatCompletionResponse): ChatResult {
        val generations = chatCompletionResponse.choices.map { res ->
            ChatGenerationImpl(convertChatChoiceToMessage(res))
        }
        return SimpleChatResultImpl(
            generations,
            LLMOutputImpl(
                chatCompletionResponse.usage.totalTokens,
                modelName
            )
        )
    }

    private fun convertChatChoiceToMessage(choice: ChatChoice): BaseMessage {
        return when (val role = choice.message.role) {
            Message.Role.USER.name -> HumanMessage(content = choice.message.content)
            Message.Role.ASSISTANT.name -> AIMessage(content = choice.message.content)
            Message.Role.SYSTEM.name -> SystemMessage(content = choice.message.content)
            else -> ChatMessage(content = choice.message.content, role)
        }
    }
}