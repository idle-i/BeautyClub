package com.example.disbeauty.data.dto

data class Order(
    var id: String = "",
    var userId: String = "",
    var userName: String = "",
    var userPhoneNumber: String = "",
    var name: String = "",
    var price: Int = -1,
    var masterId: String = "",
    var masterName: String = "",
    var masterPhoneNumber: String = "",
    var time: Long = -1,
    var category: String? = "",
    var canceled: Boolean? = false
)
