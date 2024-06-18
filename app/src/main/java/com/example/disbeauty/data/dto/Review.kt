package com.example.disbeauty.data.dto

data class Review(
    val masterId: String? = null,
    val userId: String? = null,
    val userName: String? = null,
    val orderId: String? = null,
    val rating: Int? = null,
    val message: String? = null,
    val time: Long? = null
)
