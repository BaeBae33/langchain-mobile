package com.langchain.mobile.android.core.base.output

class LLMResultImpl(override val generations: List<List<Generation>>, override val llmOutput: LLMOutputImpl?) :
    LLMResult