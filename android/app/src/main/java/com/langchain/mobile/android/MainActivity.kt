package com.langchain.mobile.android

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.langchain.mobile.android.core.base.chain.conversation.ConversationChain
import com.langchain.mobile.android.core.base.memory.ConversationBufferMemory
import com.langchain.mobile.android.core.utilities.GoogleSerperAPIWrapper
import com.langchain.mobile.android.databinding.ActivityMainBinding
import com.langchain.mobile.chatmodels.ChatOpenAI
import kotlinx.coroutines.Dispatchers
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
            lifecycleScope.launch(Dispatchers.IO) {
                show(conversation.predict("input" to "Hi there"))
                delay(21 * 1000)
                show(
                    conversation.predict("input" to "I'm doing well! Just having a conversation with an AI.")
                )
                delay(21 * 1000)
                conversation.predict("input" to "Tell me about yourself.")
            }
        }

        binding.GoogleSerperAPIWrapper.setOnClickListener {
            val googleSerperAPIWrapper = GoogleSerperAPIWrapper("xxx")
            lifecycleScope.launch(Dispatchers.IO) {
                show(googleSerperAPIWrapper("What was Obama's first name?"))
            }
        }
    }

    private fun show(str: String) {
        lifecycleScope.launch(Dispatchers.Main) {
            Toast.makeText(this@MainActivity, str, Toast.LENGTH_SHORT).show()
        }
    }

}