package com.example.disbeauty.ui.datetime

import android.os.Bundle
import com.example.disbeauty.base.BaseActivity
import com.example.disbeauty.databinding.ActivityDateTimeBinding
import java.util.Calendar

class DateTimeActivity : BaseActivity() {

    lateinit var binding: ActivityDateTimeBinding

    var selectedDateTime: Calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDateTimeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        selectedDateTime.clear()

        addOnClickListeners()
        showViews()
    }
}