package com.example.disbeauty.ui.start

import android.os.Bundle
import com.example.disbeauty.base.BaseActivity
import com.example.disbeauty.databinding.ActivityStartBinding

class StartActivity : BaseActivity() {

    lateinit var binding: ActivityStartBinding

    var currentLayoutType: LayoutType = LayoutType.SIGN_IN

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupSignInLayout()
        setupSignUpLayout()

        showViews()

        addOnClickListeners()
    }
}

enum class LayoutType {
    SIGN_IN, SIGN_UP
}