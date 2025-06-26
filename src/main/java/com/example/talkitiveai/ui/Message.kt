package com.example.talkitiveai.ui

data class Message(
    val text: String,
    val isUser: Boolean // true if sent by user, false if by AI
)
