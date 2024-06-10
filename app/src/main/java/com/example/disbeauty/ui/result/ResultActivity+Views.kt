package com.example.disbeauty.ui.result

import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.example.disbeauty.R
import com.example.disbeauty.data.TempData
import com.example.disbeauty.data.dto.Order
import com.example.disbeauty.ui.main.MainActivity
import java.text.SimpleDateFormat
import java.util.Locale

fun ResultActivity.setupViews() {
    val order: Order = TempData.currentOrder

    binding.serviceLabel.text = order.name
    binding.masterLabel.text = order.masterName

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

//    binding.priceLabel.text = "${order.price} ₽"
}

fun ResultActivity.showViews() {
    val fadeInAnimation: Animation = AnimationUtils.loadAnimation(
        this,
        R.anim.fade_in
    ).apply { duration = 1000 }

    listOf(binding.titleLabel, binding.infoLayout, binding.confirmButton).forEach {
        it.startAnimation(fadeInAnimation)
    }
}

fun ResultActivity.addOnClickListeners() {
    binding.confirmButton.setOnClickListener {
        showMainActivity()
    }
}

fun ResultActivity.showMainActivity() {
    startActivityWithFinish(MainActivity::class.java, true)
}