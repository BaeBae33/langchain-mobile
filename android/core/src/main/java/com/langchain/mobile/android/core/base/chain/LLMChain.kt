package com.langchain.mobile.android.core.base.chain

import com.langchain.mobile.android.core.base.Variable
import com.langchain.mobile.android.core.base.key
import com.langchain.mobile.android.core.base.model.BaseLanguageModel
import com.langchain.mobile.android.core.base.output.LLMResult
import com.langchain.mobile.android.core.base.prompt.BasePromptTemplate
import com.langchain.mobile.android.core.base.prompt.PromptValue
import com.langchain.mobile.android.core.base.value

open class LLMChain(
    private val llm: BaseLanguageModel,
    private val prompt: BasePromptTemplate,
    private val inputKey: String = "input",
    private val outputKey: String = "text",
) : Chain {

    override val type: String
        get() = "llm_chain"

    override val inputKeys: List<String>
        get() = listOf(inputKey)

    /**
     * Generate LLM result from inputs
     */
    fun generate(vararg variable: Variable): LLMResult {
        val inputs = prepInputs(*variable)
        val prompts = prepPrompts(*inputs.toTypedArray())
        val result = llm.generatePrompt(prompts)
        return prepOutputs(*inputs.toTypedArray(), result = result)
    }

    fun apply(vararg variable: Variable): List<Variable> {
        return createOutputs(generate(*variable))
    }

    fun predict(vararg variable: Variable): String {
        return apply(*variable).first { it.key == outputKey }.value as String
    }

    private fun prepPrompts(vararg variable: Variable): List<PromptValue> {
        return listOf(prompt.formatPrompt(*variable))
    }

    private fun prepInputs(vararg variable: Variable): List<Variable> {
        memory ?: return variable.toList()
        return memory!!.loadMemoryVariables() + variable.toList()
    }

    private fun prepOutputs(vararg variable: Variable, result: LLMResult): LLMResult {
        memory ?: return result
        memory!!.saveContext(variable.toList(), createOutputs(result))
        return result
    }

    private fun createOutputs(response: LLMResult): List<Variable> {
        return response.generations.map {
            Variable(outputKey, it.first().text)
        }
    }

}