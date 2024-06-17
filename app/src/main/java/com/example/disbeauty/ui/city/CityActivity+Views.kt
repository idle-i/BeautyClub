package com.example.disbeauty.ui.city

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.disbeauty.R
import com.example.disbeauty.adapters.CitiesAdapter
import com.example.disbeauty.data.dto.City
import com.example.disbeauty.data.dto.Service
import com.example.disbeauty.ui.main.MainActivity
import com.example.disbeauty.utils.dpToPx

fun CityActivity.showViews() {
    val fadeInAnimation: Animation = AnimationUtils.loadAnimation(
        this,
        R.anim.fade_in
    ).apply {
        duration = 1000

        setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {}

            override fun onAnimationEnd(p0: Animation?) {
                configureRecycler()

                addOnClickListeners()
            }

            override fun onAnimationRepeat(p0: Animation?) {}
        })
    }

    listOf(binding.titleLabel, binding.backButton).forEach {
        it.startAnimation(fadeInAnimation)
    }
}

fun CityActivity.configureRecycler() {
    binding.progressBar.visibility = View.VISIBLE
    binding.citiesRecycler.visibility = View.INVISIBLE

    binding.citiesRecycler.layoutManager = LinearLayoutManager(this)
    binding.citiesRecycler.addItemDecoration(
        DividerItemDecoration(
            this@configureRecycler,
            DividerItemDecoration.VERTICAL
        ).apply {
            binding.citiesRecycler.setPadding(
                dpToPx(30),
                dpToPx(30),
                dpToPx(30),
                0
            )

            setDrawable(
                ContextCompat.getDrawable(
                    this@configureRecycler,
                    R.drawable.ic_separator
                )!!
            )
        }
    )

    getCities { task ->
        val cities = mutableListOf<City>()

        task.result.forEach {
            val city = it.toObject(City::class.java)
            city.id = it.id
            cities.add(city)
        }

        binding.progressBar.visibility = View.GONE
        binding.citiesRecycler.visibility = View.VISIBLE

        binding.citiesRecycler.apply {
            adapter = CitiesAdapter(
                this@configureRecycler,
                cities
            ) {
                if (!intent.getBooleanExtra("isMaster", false)) {
                    getSharedPreferences("data", Context.MODE_PRIVATE)
                        .edit()
                        .apply {
                            putString("userCity", it.id)
                            commit()
                        }

                    startActivityWithFinish(MainActivity::class.java, true)
                } else {
                    it.id?.let { cityId ->
                        setCity(cityId) {
                            val intent = Intent()
                            intent.putExtra("userCity", it.name)
                            setResult(RESULT_OK, intent)
                            finish()
                        }
                    }
                }
            }
        }
    }
}

fun CityActivity.addOnClickListeners() {
    binding.backButton.setOnClickListener {
        finish()
    }
}
