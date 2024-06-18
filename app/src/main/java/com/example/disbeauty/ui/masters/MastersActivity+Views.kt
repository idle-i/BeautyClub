package com.example.disbeauty.ui.masters

import android.content.Context
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.disbeauty.R
import com.example.disbeauty.adapters.MastersAdapter
import com.example.disbeauty.data.TempData
import com.example.disbeauty.data.dto.Master
import com.example.disbeauty.ui.datetime.DateTimeActivity
import com.example.disbeauty.utils.dpToPx
import kotlin.math.abs

fun MastersActivity.addOnClickListeners() {
    binding.confirmButton.setOnClickListener {
        if (!noMasters) {
            val currentMaster: Master = masters[binding.mastersViewPager.currentItem]

            TempData.currentOrder.apply {
                masterId = currentMaster.id ?: ""
                masterName = currentMaster.name ?: ""
                masterPhoneNumber = currentMaster.phoneNumber ?: ""
            }

            showDateTimeActivity()
        } else {
            finish()
        }
    }
}

fun MastersActivity.showViews() {
    binding.mastersViewPager.apply {
        orientation = ViewPager2.ORIENTATION_HORIZONTAL

        offscreenPageLimit = 3
        clipToPadding = false
        clipChildren = false

        getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        setPadding(dpToPx(64), 0, dpToPx(64), 0)

        setPageTransformer { page, position ->
            page.scaleY = 1 - (0.2f * abs(position))
        }

        adapter = MastersAdapter(this@showViews, masters)
    }

    val fadeInAnimation: Animation = AnimationUtils.loadAnimation(
        this,
        R.anim.fade_in
    ).apply { duration = 1000 }

    val fadeInSlideInAnimation: Animation = AnimationUtils.loadAnimation(
        this,
        R.anim.fade_in_slide_in_from_bottom
    ).apply {
        setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {}

            override fun onAnimationEnd(p0: Animation?) {
                getMasters(
                    TempData.currentOrder.id,
                    getSharedPreferences("data", Context.MODE_PRIVATE)
                        .getString(
                            "userCity",
                            "KjUCtYpeCDMSSYPu4oVv"
                        ) ?: "KjUCtYpeCDMSSYPu4oVv"
                ) {
                    val localMasters = it.result.toObjects(Master::class.java)

                    addOnClickListeners()

                    localMasters
                        .forEach { master ->
                            getReviews(master.id ?: "") { reviews ->
                                if (reviews.isNotEmpty()) {
                                    master.rating = (reviews.mapNotNull { review -> review.rating }
                                        .sum() / reviews.size).toFloat()
                                }
                                masters.add(master)

                                binding.mastersViewPager.adapter?.notifyDataSetChanged()
                            }
                        }

                    binding.masterProgressBar.visibility = View.GONE

                    noMasters = localMasters.isEmpty()

                    if (localMasters.isEmpty()) {
                        binding.noMastersLabel.visibility = View.VISIBLE
                    } else {
                        binding.confirmButton.visibility = View.VISIBLE
                    }

                }
            }

            override fun onAnimationRepeat(p0: Animation?) {}
        })
    }

    listOf(binding.titleLabel, binding.subTitleLabel).forEach {
        it.startAnimation(fadeInAnimation)
    }

    listOf(binding.mastersBackground, binding.masterProgressBar, binding.confirmButton).forEach {
        it.startAnimation(fadeInSlideInAnimation)
    }
}

private fun MastersActivity.showDateTimeActivity() {
    startActivity(DateTimeActivity::class.java)
}