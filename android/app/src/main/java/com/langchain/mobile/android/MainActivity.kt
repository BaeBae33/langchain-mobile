package com.langchain.mobile.android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.langchain.mobile.android.core.base.chain.LLMChain
import com.langchain.mobile.android.core.base.message.HumanMessage
import com.langchain.mobile.android.core.base.message.SystemMessage
import com.langchain.mobile.android.core.base.prompt.PromptTemplate
import com.langchain.mobile.android.databinding.ActivityMainBinding
import com.langchain.mobile.chatmodels.ChatOpenAI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.chatOpenAI.setOnClickListener {
            val chat = ChatOpenAI(
                "xxxx"
            ) {
                temperature = 0.0
            }
            val messages = listOf(
                SystemMessage(content = "You are a helpful assistant that translates English to French."),
                HumanMessage(content = "Translate this sentence from English to French. I love programming."),
            )
            GlobalScope.launch(Dispatchers.IO) {
                val llmChain = LLMChain(
                    llm = chat,
                    prompt = PromptTemplate("What is a good name for a company that makes {product}?")
                )
                val result = llmChain.apply("product" to "colorful socks")
            }
        }
    }

}