package com.langchain.mobile.android.core.chat

import com.langchain.mobile.android.core.base.output.LLMOutputImpl

class SimpleChatResultImpl(override val generations: List<ChatGeneration>, override val llmOutput: LLMOutputImpl) :
    ChatResult