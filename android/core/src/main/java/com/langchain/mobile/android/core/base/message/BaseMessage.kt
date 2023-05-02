package com.langchain.mobile.android.core.base.message

/**
 * Message object.
 */
sealed class BaseMessage(
    open val content: String,
) {
    /**
     * Type of the message, used for serialization.
     */
    open val type: String
        get() = "base"
}

/**
 * Type of message that is spoken by the human.
 */
class HumanMessage(override val content: String) : BaseMessage(content) {
    /**
     * Type of the message, used for serialization.
     */
    override val type: String
        get() = "human"
}

/**
 * Type of message that is spoken by the AI.
 */
class AIMessage(override val content: String) : BaseMessage(content) {
    /**
     * Type of the message, used for serialization.
     */
    override val type: String
        get() = "ai"
}

/**
 * Type of message that is a system message.
 */
class SystemMessage(override val content: String) : BaseMessage(content) {
    /**
     * Type of the message, used for serialization.
     */
    override val type: String
        get() = "system"
}

/**
 * Type of message with arbitrary speaker.
 */
class ChatMessage(override val content: String, val role: String) : BaseMessage(content) {
    /**
     * Type of the message, used for serialization.
     */
    override val type: String
        get() = "chat"
}