package com.langchain.mobile.android.core.base.memory

import com.langchain.mobile.android.core.base.Variable
import com.langchain.mobile.android.core.base.key
import com.langchain.mobile.android.core.base.value

abstract class BaseChatMemory(
    val chatMemory: BaseChatMessageHistory = ChatMessageHistory(),
    val inputKey: String? = null,
    var outputKey: String? = null,
) :
    BaseMemory {

    override fun saveContext(inputs: List<Variable>, outputs: List<Variable>) {
        val (inputStr, outputStr) = getInputOutput(inputs, outputs)
        chatMemory.addUserMessage(inputStr)
        chatMemory.addAIMessage(outputStr)
    }

    override fun clear() {
        chatMemory.clear()
    }

    fun getInputOutput(inputs: List<Variable>, outputs: List<Variable>): Pair<String, String> {
        val promptInputKey =
            inputKey ?: getPromptInputKey(*inputs.toTypedArray(), memoryVariable = memoryVariables())
        outputKey = if (outputKey == null) {
            if (outputs.size != 1) {
                throw Exception("One output key expected got ${outputs.map { it.key }}")
            }
            outputs.first().key
        } else {
            outputKey
        }
        return inputs.first { it.key == promptInputKey }.value as String to outputs.first { it.key == outputKey }.value as String
    }
}

fun getPromptInputKey(vararg inputs: Variable, memoryVariable: List<String>): String {
    val promptInputKeys = inputs.map { it.key }.toSet() - memoryVariable.toSet()
    if (promptInputKeys.size != 1) {
        throw Exception("One input key expected got $promptInputKeys")
    }
    return promptInputKeys.first()
}