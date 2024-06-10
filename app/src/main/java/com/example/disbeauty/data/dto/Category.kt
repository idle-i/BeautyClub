package com.example.disbeauty.data.dto

data class Category(
    var id: String? = null,
    val name: String? = null,
    var services: List<Service>? = null
)
