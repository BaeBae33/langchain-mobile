package com.langchain.mobile.android.core.base.prompt

import org.apache.commons.text.StringSubstitutor

class PromptTemplate(private val template: String) : StringPromptTemplate {

    override val type: String
        get() = "prompt"


    override fun format(vararg variables: Pair<String, Any>): String {
        return StringSubstitutor(variables.toMap(), "{", "}").replace(template)
    }
}