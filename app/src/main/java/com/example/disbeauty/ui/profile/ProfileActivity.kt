package com.example.disbeauty.ui.profile

import android.os.Bundle
import com.example.disbeauty.base.BaseActivity
import com.example.disbeauty.data.dto.Master
import com.example.disbeauty.databinding.ActivityProfileBinding

class ProfileActivity : BaseActivity() {

    lateinit var binding: ActivityProfileBinding

    var workingStartTime: List<Int> = listOf()
    var workingEndTime: List<Int> = listOf()

    var master: Master = Master()

    var isEditing: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showViews()
    }
}