package com.langchain.mobile.android.core.base.prompt

private const val DEFAULT_TEMPLATE =
    """The following is a friendly conversation between a human and an AI. The AI is talkative and provides lots of specific details from its context. If the AI does not know the answer to a question, it truthfully says it does not know.

Current conversation:
{history}
Human: {input}
AI:"""

val defaultPrompt = PromptTemplate(DEFAULT_TEMPLATE)