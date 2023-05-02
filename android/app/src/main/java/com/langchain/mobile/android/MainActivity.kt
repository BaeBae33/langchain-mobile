package com.langchain.mobile.android

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.langchain.mobile.android.core.base.chain.conversation.ConversationChain
import com.langchain.mobile.android.core.base.memory.ConversationBufferMemory
import com.langchain.mobile.android.databinding.ActivityMainBinding
import com.langchain.mobile.chatmodels.ChatOpenAI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
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
                maxTokens = 1000
            }
            val conversation = ConversationChain(
                llm = chat,
                memory = ConversationBufferMemory()
            )
            GlobalScope.launch(Dispatchers.IO) {
                Log.i("MainActivity",
                    conversation.predict("input" to "Hi there")
                )
                delay(21*1000)
                Log.i(
                    "MainActivity",
                    conversation.predict("input" to "I'm doing well! Just having a conversation with an AI.")
                )
                delay(21*1000)
                Log.i("MainActivity",
                    conversation.predict("input" to "Tell me about yourself.")
                )
            }
        }
    }

}