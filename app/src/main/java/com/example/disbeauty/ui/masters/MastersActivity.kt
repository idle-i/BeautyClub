package com.example.disbeauty.ui.masters

import android.os.Bundle
import com.example.disbeauty.base.BaseActivity
import com.example.disbeauty.data.dto.Master
import com.example.disbeauty.databinding.ActivityMastersBinding

class MastersActivity : BaseActivity() {

    lateinit var binding: ActivityMastersBinding

    var masters: List<Master> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMastersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        addOnClickListeners()
        showViews()
    }
}