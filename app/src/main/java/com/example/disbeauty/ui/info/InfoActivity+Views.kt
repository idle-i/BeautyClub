package com.example.disbeauty.ui.info

import android.app.Activity
import android.content.Intent
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.example.disbeauty.R
import com.example.disbeauty.ui.chat.ChatActivity
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


fun InfoActivity.showViews() {
    val fadeInAnimation: Animation = AnimationUtils.loadAnimation(
        this,
        R.anim.fade_in
    ).apply {
        duration = 1000

        setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {}

            override fun onAnimationEnd(p0: Animation?) {
                getOrder(orderId) { order ->
                    binding.mainContainer.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE

                    binding.serviceLabel.text = order.name

                    binding.masterTitleLabel.text = getString(
                        if (isMaster)
                            R.string.stringClient
                        else
                            R.string.stringMaster
                    )
                    binding.masterLabel.text = if (isMaster) order.userName else order.masterName

                    if (order.time < Calendar.getInstance().timeInMillis || order.canceled == true) {
                        binding.cancelButtonLayout.visibility = View.GONE
                    }

                    binding.dateLabel.text =
                        SimpleDateFormat(
                            "dd MMM yyyy",
                            Locale.getDefault()
                        ).format(order.time)

                    binding.timeLabel.text =
                        SimpleDateFormat(
                            "HH:mm",
                            Locale.getDefault()
                        ).format(order.time)

                    this@showViews.order = order
                }

                addOnClickListeners()
            }

            override fun onAnimationRepeat(p0: Animation?) {}
        })
    }

    binding.contentView.startAnimation(fadeInAnimation)
}

fun InfoActivity.addOnClickListeners() {
    binding.backButton.setOnClickListener {
        finish()
    }

    binding.chatButton.setOnClickListener {
        order?.let { order ->
            Intent(this, ChatActivity::class.java).apply {
                putExtra("recipientId", if (isMaster) order.userId else order.masterId)

                startActivity(this)
            }
        }
    }

    binding.cancelButton.setOnClickListener {
        cancelOrder(orderId) {
            val intent = Intent()
            intent.putExtra("shouldUpdate", true)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
}
