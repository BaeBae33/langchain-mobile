package com.langchain.mobile.android.core.base

typealias Variable = Pair<String, Any>

val Variable.key
    get() = first

val Variable.value
    get() = second