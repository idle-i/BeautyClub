package com.example.disbeauty.ui.result

import android.os.Bundle
import com.example.disbeauty.base.BaseActivity
import com.example.disbeauty.databinding.ActivityResultBinding

class ResultActivity : BaseActivity() {

    lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
        showViews()
        addOnClickListeners()
    }

    override fun onBackPressed() {
        super.onBackPressed()

        showMainActivity()
    }
}