package com.langchain.mobile.android.core.base.prompt

import com.langchain.mobile.android.core.base.Variable
import org.apache.commons.text.StringSubstitutor

class PromptTemplate(private val template: String) : StringPromptTemplate {

    override val type: String
        get() = "prompt"


    override fun format(vararg variable: Variable): String {
        return StringSubstitutor(variable.toMap(), "{", "}").replace(template)
    }
}