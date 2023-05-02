package com.langchain.mobile.android.core.base.chain

import com.langchain.mobile.android.core.base.memory.BaseMemory

/**
 * Base interface that all chains should implement.
 */
interface Chain {

    val type: String

    val memory: BaseMemory?
        get() = null

    val inputKeys: List<String>

}