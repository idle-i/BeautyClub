package com.example.disbeauty.data.dto

data class Master(
    val id: String? = null,
    val name: String? = null,
    val avatar: String? = null,
    val services: MutableList<String>? = null,
    var phoneNumber: String? = null,
    var workingStartHour: Int? = 9,
    var workingStartMinute: Int? = 0,
    var workingEndHour: Int? = 21,
    var workingEndMinute: Int? = 0,
    var city: String? = "KjUCtYpeCDMSSYPu4oVv"
)
