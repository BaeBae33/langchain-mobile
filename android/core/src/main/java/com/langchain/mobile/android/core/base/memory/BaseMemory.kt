package com.langchain.mobile.android.core.base.memory

import com.langchain.mobile.android.core.base.Variable

/**
 * Base interface for memory in chains.
 */
interface BaseMemory {

    /**
     * Input keys this memory class will load dynamically.
     */
    fun memoryVariables(): List<String>

    /**
     * Return key-value pairs given the text input to the chain
     * If None, return all memories
     */
    fun loadMemoryVariables(vararg variable: Variable): List<Variable>

    /**
     * Save the context of this model run to memory.
     */
    fun saveContext(inputs: List<Variable>, outputs: List<Variable>)

    /**
     * Clear memory contents.
     */
    fun clear()
}