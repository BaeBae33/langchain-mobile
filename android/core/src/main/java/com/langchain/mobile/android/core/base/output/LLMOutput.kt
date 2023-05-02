package com.langchain.mobile.android.core.base.output

interface LLMOutput {
    val tokenUsage: Long
    val modelName: String
}