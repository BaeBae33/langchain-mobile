package com.langchain.mobile.android.core.base.chain.conversation

import com.langchain.mobile.android.core.base.chain.LLMChain
import com.langchain.mobile.android.core.base.memory.BaseMemory
import com.langchain.mobile.android.core.base.memory.ConversationBufferMemory
import com.langchain.mobile.android.core.base.model.BaseLanguageModel
import com.langchain.mobile.android.core.base.prompt.BasePromptTemplate
import com.langchain.mobile.android.core.base.prompt.defaultPrompt

class ConversationChain(
    llm: BaseLanguageModel,
    override val memory: BaseMemory = ConversationBufferMemory(),
    prompt: BasePromptTemplate = defaultPrompt,
    inputKey: String = "input",
    outputKey: String = "response",
) : LLMChain(llm, prompt, inputKey, outputKey)