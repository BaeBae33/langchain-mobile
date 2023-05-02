package com.langchain.mobile.android.core.base.prompt

/**
 * Base interface for all prompt templates, returning a prompt.
 */
interface BasePromptTemplate {

    /**
     * Return the prompt type key.
     */
    val type: String

    /**
     * Create Chat Messages.
     */
    fun formatPrompt(vararg pair: Pair<String, Any>): PromptValue

    /**
     * Format the prompt with the inputs.

    Args:
    kwargs: Any arguments to be passed to the prompt template.

    Returns:
    A formatted string.

    Example:

    .. code-block:: python

    prompt.format(variable1="foo")
     */
    fun format(vararg pair: Pair<String, Any>): String
}