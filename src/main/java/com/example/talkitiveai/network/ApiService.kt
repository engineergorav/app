package com.example.talkitiveai.network

import android.os.Handler
import android.os.Looper

object ApiService {
    // New function with system prompt for personality
    fun sendMessageWithSystemPrompt(prompt: String, message: String, callback: (String?) -> Unit) {
        Handler(Looper.getMainLooper()).postDelayed({
            val personalityPrefix = when {
                prompt.contains("loving", ignoreCase = true) -> "💖 [Loving AI]:"
                prompt.contains("funny", ignoreCase = true) -> "😂 [Funny AI]:"
                prompt.contains("romantic", ignoreCase = true) -> "💘 [Romantic AI]:"
                prompt.contains("serious", ignoreCase = true) -> "🧠 [Serious AI]:"
                prompt.contains("mentor", ignoreCase = true) -> "📘 [Mentor AI]:"
                else -> "[AI]:"
            }

            val mockReply = when {
                message.contains("hello", ignoreCase = true) -> "$personalityPrefix Hello! So happy to see you 😄"
                message.contains("love", ignoreCase = true) -> "$personalityPrefix Love is the most beautiful feeling ❤️"
                message.contains("your name", ignoreCase = true) -> "$personalityPrefix I'm your AI friend, always here for you!"
                else -> "$personalityPrefix This is a mock reply based on your message: \"$message\""
            }

            callback(mockReply)
        }, 1000)
    }
}
