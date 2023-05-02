package com.langchain.mobile.android.core.chat

import com.langchain.mobile.android.core.base.message.BaseMessage
import com.langchain.mobile.android.core.base.output.Generation

/**
 * Output of a single generation.
 */
interface ChatGeneration : Generation {
    val message: BaseMessage

    override val text: String
        get() = ""
}