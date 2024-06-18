package com.example.disbeauty.ui.chat

import android.os.Bundle
import com.example.disbeauty.base.BaseActivity
import com.example.disbeauty.data.dto.Message
import com.example.disbeauty.databinding.ActivityChatBinding

class ChatActivity : BaseActivity() {

    lateinit var binding: ActivityChatBinding

    var recipientId: String = ""
    var messages: MutableList<Message> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recipientId = intent.getStringExtra("recipientId").toString()

        setupViews()
        showViews()
    }
}