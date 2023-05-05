package com.langchain.mobile.android.core.base.tool

class Tool(
    private val name: String,
    private val description: String,
    private val func: (String) -> String,
) :
    BaseTool {

    operator fun invoke(str: String): String {
        return func(str)
    }
}