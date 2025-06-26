package com.example.talkitiveai.ui

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.talkitiveai.R
import com.example.talkitiveai.ui.MessageAdapter
import com.example.talkitiveai.ui.Message
import com.example.talkitiveai.network.ApiService // Import the API service
import android.os.Handler
import android.os.Looper

class ChatActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var messageInput: EditText
    private lateinit var sendButton: ImageButton
    private lateinit var adapter: MessageAdapter
    private val messages = mutableListOf<Message>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        recyclerView = findViewById(R.id.chatRecyclerView)
        messageInput = findViewById(R.id.messageInput)
        sendButton = findViewById(R.id.sendButton)

        adapter = MessageAdapter(messages)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        sendButton.setOnClickListener {
            val userMessage = messageInput.text.toString().trim()
            if (userMessage.isNotEmpty()) {
                addMessage(userMessage, isUser = true)
                messageInput.setText("")

                // Call the API and get AI reply
                ApiService.sendMessage(userMessage) { reply ->
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
}
