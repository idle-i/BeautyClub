package com.example.disbeauty.ui.services_change

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.disbeauty.R
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