package com.example.disbeauty.ui.main

import android.os.Bundle
import com.example.disbeauty.base.BaseActivity
import com.example.disbeauty.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showViews()
        addOnClickListeners()
    }
}