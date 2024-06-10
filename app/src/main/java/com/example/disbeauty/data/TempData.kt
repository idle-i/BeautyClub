package com.example.disbeauty.data

import com.example.disbeauty.data.dto.Master
import com.example.disbeauty.data.dto.Order
import com.example.disbeauty.data.dto.User

object TempData {
    var currentUser: User = User()
    var currentOrder: Order = Order()
    var currentMaster: Master = Master()
}