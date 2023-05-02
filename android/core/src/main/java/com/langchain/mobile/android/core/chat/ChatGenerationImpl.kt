package com.langchain.mobile.android.core.chat

import com.langchain.mobile.android.core.base.message.BaseMessage

class ChatGenerationImpl(override val message: BaseMessage, override val text: String = "") : ChatGeneration