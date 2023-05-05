package com.langchain.mobile.android

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.langchain.mobile.android.core.base.chain.conversation.ConversationChain
import com.langchain.mobile.android.core.base.memory.ConversationBufferMemory
import com.langchain.mobile.android.core.base.message.HumanMessage
import com.langchain.mobile.android.core.base.tool.Tool
import com.langchain.mobile.android.core.utilities.GoogleSerperAPIWrapper
import com.langchain.mobile.android.databinding.ActivityMainBinding
import com.langchain.mobile.chatmodels.ChatOpenAI
import com.langchain.mobile.native_feature.PackageListTool
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
            val tool = Tool(
                name = "I'm Feeling Lucky",
                description = "Search Google and return the first result.",
                func = googleSerperAPIWrapper::invoke
            )
            lifecycleScope.launch(Dispatchers.IO) {
                show(tool("python"))
            }
        }

        binding.PackageListTool.setOnClickListener {
            val chat = ChatOpenAI(
                "xxx"
            ) {
                temperature = 0.0
                maxTokens = 1000
            }
            val packageListTool = PackageListTool()
            // save token
            val packages = packageListTool().split(",").subList(0, 3).joinToString(",")

            lifecycleScope.launch(Dispatchers.IO) {
                val llmResult =
                    chat.generate(HumanMessage("How many non-system apps in the list? App list: $packages"))
                show(llmResult.generations.first().first().text)
            }
        }
    }

    private fun show(str: String) {
        lifecycleScope.launch(Dispatchers.Main) {
            Toast.makeText(this@MainActivity, str, Toast.LENGTH_SHORT).show()
        }
    }

}