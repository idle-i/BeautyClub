package com.example.disbeauty.ui.appointments

import android.os.Bundle
import com.example.disbeauty.base.BaseActivity
import com.example.disbeauty.data.dto.Order
import com.example.disbeauty.databinding.ActivityAppointmentsBinding

class AppointmentsActivity : BaseActivity() {

    lateinit var binding: ActivityAppointmentsBinding

    var history: List<Order> = listOf()
    var selectedType: HistoryType = HistoryType.UPCOMING

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAppointmentsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
        showViews()
    }
}