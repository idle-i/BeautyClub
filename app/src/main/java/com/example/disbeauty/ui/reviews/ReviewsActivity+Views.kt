package com.example.disbeauty.ui.reviews

import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.disbeauty.R
import com.example.disbeauty.adapters.ReviewsAdapter


fun ReviewsActivity.showViews() {
    val fadeInAnimation: Animation = AnimationUtils.loadAnimation(
        this,
        R.anim.fade_in
    ).apply {
        duration = 1000

        setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {}

            override fun onAnimationEnd(p0: Animation?) {
                masterId?.let {
                    getReviews(it) { reviews ->
                        binding.progressBar.visibility = View.GONE

                        binding.reviewsRecycler.apply {
                            layoutManager = LinearLayoutManager(this@showViews)

                            adapter = ReviewsAdapter(
                                this@showViews,
                                reviews
                            )
                        }
                    }
                }

                addOnClickListeners()
            }

            override fun onAnimationRepeat(p0: Animation?) {}
        })
    }

    binding.mainContainer.startAnimation(fadeInAnimation)
}

fun ReviewsActivity.addOnClickListeners() {
    binding.backButton.setOnClickListener {
        finish()
    }
}
