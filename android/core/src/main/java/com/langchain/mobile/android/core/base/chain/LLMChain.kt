package com.langchain.mobile.android.core.base.chain

import com.langchain.mobile.android.core.base.model.BaseLanguageModel
import com.langchain.mobile.android.core.base.output.LLMResult
import com.langchain.mobile.android.core.base.prompt.BasePromptTemplate
import com.langchain.mobile.android.core.base.prompt.PromptValue

class LLMChain(
    private val llm: BaseLanguageModel,
    private val prompt: BasePromptTemplate,
    private val outputKey: String = "text",
) : Chain {

    override val type: String
        get() = "llm_chain"

    /**
     * Generate LLM result from inputs
     */
    fun generate(vararg variables: Pair<String, Any>): LLMResult {
        val prompts = prepPrompts(*variables)
        return llm.generatePrompt(prompts)
    }

    fun apply(vararg variables: Pair<String, Any>): LLMResult {
        return generate(*variables)
    }

    private fun prepPrompts(vararg variables: Pair<String, Any>): List<PromptValue> {
        return variables.map {
            prompt.formatPrompt(it)
        }
    }

}