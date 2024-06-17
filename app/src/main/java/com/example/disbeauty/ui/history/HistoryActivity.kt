package com.example.disbeauty.ui.history

import android.content.Intent
import android.os.Bundle
import com.example.disbeauty.base.BaseActivity
import com.example.disbeauty.data.dto.Order
import com.example.disbeauty.databinding.ActivityHistoryBinding

class HistoryActivity : BaseActivity() {

    lateinit var binding: ActivityHistoryBinding

    var history: List<Order> = listOf()
    var selectedType: HistoryType = HistoryType.UPCOMING

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
        showViews()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && data?.getBooleanExtra("shouldUpdate", false) == true) {
            showViews()
        }
    }
}