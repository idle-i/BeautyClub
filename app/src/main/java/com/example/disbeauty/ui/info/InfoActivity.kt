package com.example.disbeauty.ui.info

import android.os.Bundle
import com.example.disbeauty.base.BaseActivity
import com.example.disbeauty.data.dto.Order
import com.example.disbeauty.databinding.ActivityInfoBinding

class InfoActivity : BaseActivity() {

    lateinit var binding: ActivityInfoBinding

    var orderId: String = ""
    var order: Order? = null
    var isMaster: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        orderId = intent.getStringExtra("orderId") ?: ""
        isMaster = intent.getBooleanExtra("isMaster", false)

        showViews()
    }
}