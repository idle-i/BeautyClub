package com.example.disbeauty.ui.chat

import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.disbeauty.R
import com.example.disbeauty.adapters.ChatAdapter

fun ChatActivity.setupViews() {
    binding.messagesRecycler.apply {
        layoutManager = LinearLayoutManager(
            this@setupViews,
            LinearLayoutManager.VERTICAL,
            true
        )
    }
}

fun ChatActivity.showViews() {
    val fadeInAnimation: Animation = AnimationUtils.loadAnimation(
        this,
        R.anim.fade_in
    ).apply {
        duration = 1000

        setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {}

            override fun onAnimationEnd(p0: Animation?) {
                getMessages {
                    messages = it.toMutableList()

                    binding.chatProgressBar.visibility = View.GONE

                    binding.messagesRecycler.visibility = View.VISIBLE
                    binding.messagesRecycler.adapter = ChatAdapter(
                        this@showViews,
                        messages
                    )

                    addOnClickListeners()
                }
            }

            override fun onAnimationRepeat(p0: Animation?) {}
        })
    }

    binding.mainContainer.startAnimation(fadeInAnimation)
}

private fun ChatActivity.addOnClickListeners() {
    binding.backButton.setOnClickListener {
        finish()
    }

    binding.inputSendButton.setOnClickListener {
        val message = binding.inputField.text.toString()

        if (message.isNotEmpty() && message.isNotBlank()) {
            binding.inputField.text = null

            onSendMessagePressed(message)
        }
    }
}

private fun ChatActivity.onSendMessagePressed(text: String) {
    sendMessage(text) {
        messages.add(0, it)

        binding.messagesRecycler.adapter?.notifyItemInserted(0)
        binding.messagesRecycler.smoothScrollToPosition(0)
    }
}
