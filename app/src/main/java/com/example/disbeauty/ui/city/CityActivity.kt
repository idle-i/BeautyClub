package com.example.disbeauty.ui.city

import android.os.Bundle
import com.example.disbeauty.base.BaseActivity
import com.example.disbeauty.databinding.ActivityCityBinding

class CityActivity : BaseActivity() {

    lateinit var binding: ActivityCityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showViews()
    }
}