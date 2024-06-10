package com.example.disbeauty.ui.main

import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.disbeauty.R
import com.example.disbeauty.adapters.CategoriesAdapter
import com.example.disbeauty.adapters.ServicesAdapter
import com.example.disbeauty.data.TempData
import com.example.disbeauty.data.dto.Category
import com.example.disbeauty.data.dto.Service
import com.example.disbeauty.data.firebase.FirebaseInstances
import com.example.disbeauty.ui.history.HistoryActivity
import com.example.disbeauty.ui.loading.LoadingActivity
import com.example.disbeauty.ui.masters.MastersActivity
import com.example.disbeauty.utils.dpToPx

fun MainActivity.showViews() {
    val fadeInAnimation: Animation = AnimationUtils.loadAnimation(
        this,
        R.anim.fade_in
    ).apply { duration = 1000 }

    val fadeInSlideInAnimation: Animation = AnimationUtils.loadAnimation(
        this,
        R.anim.fade_in_slide_in_from_bottom
    ).apply {
        setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {}

            override fun onAnimationEnd(p0: Animation?) {
                configureRecycler(MainRecyclerState.CATEGORIES)
            }

            override fun onAnimationRepeat(p0: Animation?) {}
        })
    }

    listOf(binding.titleLabel, binding.subTitleLabel).forEach {
        it.startAnimation(fadeInAnimation)
    }

    listOf(
        binding.servicesBackground,
        binding.mainProgressBar,
        binding.bottomShadowView,
        binding.logOutButton,
        binding.historyButton
    ).forEach {
        it.startAnimation(fadeInSlideInAnimation)
    }
}

fun MainActivity.configureRecycler(state: MainRecyclerState) {
    binding.mainProgressBar.visibility = View.VISIBLE
    binding.mainRecyclerView.visibility = View.INVISIBLE

    binding.mainRecyclerView.layoutManager = LinearLayoutManager(this)
    binding.mainRecyclerView.addItemDecoration(
        DividerItemDecoration(
            this@configureRecycler,
            DividerItemDecoration.VERTICAL
        ).apply {
            binding.mainRecyclerView.setPadding(
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

    if (state == MainRecyclerState.CATEGORIES) {
        binding.backView.visibility = View.GONE

        getCategories { task ->
            val categories: MutableList<Category> = mutableListOf()

            task.result.forEach {
                val category = it.toObject(Category::class.java)
                category.id = it.id
                categories.add(category)
            }

            binding.mainProgressBar.visibility = View.GONE
            binding.mainRecyclerView.visibility = View.VISIBLE

            binding.mainRecyclerView.apply {
                adapter = CategoriesAdapter(
                    this@configureRecycler,
                    categories
                ) {
                    TempData.currentOrder.apply {
                        category = it.id
                    }

                    configureRecycler(MainRecyclerState.SERVICES)
                }
            }
        }
    } else {
        getServices(TempData.currentOrder.category ?: "") { task ->
            val services: MutableList<Service> = mutableListOf()

            task.result.forEach {
                val service = it.toObject(Service::class.java)
                service.id = it.id
                services.add(service)
            }

            binding.mainProgressBar.visibility = View.GONE
            binding.mainRecyclerView.visibility = View.VISIBLE

            binding.backView.visibility = View.VISIBLE

            binding.mainRecyclerView.apply {
                adapter = ServicesAdapter(
                    this@configureRecycler,
                    services
                ) {
                    TempData.currentOrder.apply {
                        id = it.id ?: ""
                        userId = FirebaseInstances.auth.currentUser?.uid ?: ""
                        userName = TempData.currentUser.name ?: ""
                        userPhoneNumber = TempData.currentUser.phoneNumber ?: ""
                        name = it.name ?: ""
                        price = it.price ?: -1
                    }

                    showMasterActivity()
                }
            }
        }
    }
}

fun MainActivity.addOnClickListeners() {
    binding.logOutButton.setOnClickListener {
        FirebaseInstances.auth.signOut()

        showLoadingActivity()
    }

    binding.historyButton.setOnClickListener {
        showHistoryActivity()
    }

    binding.backView.setOnClickListener {
        configureRecycler(MainRecyclerState.CATEGORIES)
    }
}

private fun MainActivity.showLoadingActivity() {
    startActivityWithFinish(LoadingActivity::class.java, true)
}

private fun MainActivity.showHistoryActivity() {
    startActivity(HistoryActivity::class.java)
}

private fun MainActivity.showMasterActivity() {
    startActivity(MastersActivity::class.java)
}

enum class MainRecyclerState {
    CATEGORIES, SERVICES
}