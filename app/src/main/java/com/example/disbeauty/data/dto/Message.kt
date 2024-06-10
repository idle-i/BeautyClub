package com.example.disbeauty.data.dto

data class Message(
    val fromUserId: String? = null,
    val toUserId: String? = null,
    val text: String? = null,
    val timestamp: Long? = null
)
