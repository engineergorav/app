package com.example.talkitiveai.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.talkitiveai.R
import com.example.talkitiveai.network.ApiService

class ChatActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var messageInput: EditText
    private lateinit var sendButton: ImageButton
    private lateinit var adapter: MessageAdapter
    private val messages = mutableListOf<Message>()

    // Personality prompt for system message
    private var personalityPrompt = "You are a helpful assistant."

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        recyclerView = findViewById(R.id.chatRecyclerView)
        messageInput = findViewById(R.id.messageInput)
        sendButton = findViewById(R.id.sendButton)

        adapter = MessageAdapter(messages)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // ✅ Receive personality mode from intent
        val selectedMode = intent.getStringExtra("chat_mode") ?: "Loving"
        personalityPrompt = getPromptForMode(selectedMode)

        // Add system message to show mode context
        addMessage("Mode: $selectedMode", isUser = false)

        sendButton.setOnClickListener {
            val userMessage = messageInput.text.toString().trim()
            if (userMessage.isNotEmpty()) {
                addMessage(userMessage, isUser = true)
                messageInput.setText("")

                // ✅ Send both the system prompt and user message to API
                ApiService.sendMessageWithSystemPrompt(personalityPrompt, userMessage) { reply ->
                    Handler(Looper.getMainLooper()).post {
                        if (reply != null) {
                            addMessage(reply, isUser = false)
                        } else {
                            addMessage("Failed to get response from AI.", isUser = false)
                        }
                    }
                }
            }
        }
    }

    private fun addMessage(content: String, isUser: Boolean) {
        messages.add(Message(content, isUser))
        adapter.notifyItemInserted(messages.size - 1)
        recyclerView.scrollToPosition(messages.size - 1)
    }

    // ✅ Define prompts for each mode
    private fun getPromptForMode(mode: String): String {
        return when (mode) {
            "Loving" -> "You are a loving and caring AI partner who gives warm and affectionate replies."
            "Funny" -> "You are a funny and sarcastic AI who loves to joke and make people laugh."
            "Romantic" -> "You are a romantic and charming AI who flirts sweetly and expresses love."
            "Serious" -> "You are a serious and thoughtful AI who gives deep, honest, and mature replies."
            "Mentor" -> "You are an experienced mentor who gives wise advice and encouragement."
            else -> "You are a helpful assistant."
        }
    }
}
