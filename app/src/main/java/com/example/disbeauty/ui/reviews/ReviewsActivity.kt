package com.example.disbeauty.ui.reviews

import android.os.Bundle
import com.example.disbeauty.base.BaseActivity
import com.example.disbeauty.databinding.ActivityReviewsBinding

class ReviewsActivity : BaseActivity() {

    lateinit var binding: ActivityReviewsBinding

    var masterId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityReviewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        masterId = intent.getStringExtra("masterId")

        showViews()
    }
}