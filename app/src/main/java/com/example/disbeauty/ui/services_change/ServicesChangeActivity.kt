package com.example.disbeauty.ui.services_change

import android.os.Bundle
import com.example.disbeauty.base.BaseActivity
import com.example.disbeauty.databinding.ActivityServicesChangeBinding

class ServicesChangeActivity : BaseActivity() {

    lateinit var binding: ActivityServicesChangeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityServicesChangeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showViews()

    }
}