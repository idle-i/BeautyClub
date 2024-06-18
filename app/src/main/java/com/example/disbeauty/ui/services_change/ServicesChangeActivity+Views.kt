package com.example.disbeauty.ui.services_change

import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.disbeauty.R
import com.example.disbeauty.adapters.ServicesChangeCategoryAdapter
import com.example.disbeauty.data.TempData
import com.example.disbeauty.data.dto.Category
import com.example.disbeauty.data.dto.Service

fun ServicesChangeActivity.showViews() {
    val fadeInAnimation: Animation = AnimationUtils.loadAnimation(
        this,
        R.anim.fade_in
    ).apply {
        duration = 1000

        setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {}

            override fun onAnimationEnd(p0: Animation?) {
                getMaster { master ->
                    TempData.currentMaster = master

                    getCategories { categoriesTask ->
                        val categories = mutableListOf<Category>()

                        categoriesTask.result.forEach {
                            val category = it.toObject(Category::class.java)
                            category.id = it.id
                            categories.add(category)
                        }

                        categories.forEachIndexed { index, category ->
                            getServices(category.id ?: "") { servicesTask ->
                                val services = mutableListOf<Service>()
                                servicesTask.result.forEach {
                                    val service = it.toObject(Service::class.java)
                                    service.id = it.id
                                    services.add(service)
                                }

                                categories[index].services = services

                                if (index == categories.size - 1) {
                                    binding.progressBar.visibility = View.GONE

                                    binding.categoriesRecycler.apply {
                                        visibility = View.VISIBLE

                                        layoutManager = LinearLayoutManager(this@showViews)

                                        adapter = ServicesChangeCategoryAdapter(
                                            this@showViews,
                                            categories
                                        ) { service, isSelected ->
                                            updateMasterService(service, isSelected)
                                        }

                                        println(master)
                                        println(categories)
                                    }
                                }
                            }
                        }
                    }
                }

                addOnClickListeners()
            }

            override fun onAnimationRepeat(p0: Animation?) {}
        })
    }

    binding.mainContainer.startAnimation(fadeInAnimation)
}


fun ServicesChangeActivity.addOnClickListeners() {
    binding.backButton.setOnClickListener {
        finish()
    }
}

private fun ServicesChangeActivity.updateMasterService(service: Service, isSelected: Boolean) {
    val services = TempData.currentMaster.services ?: mutableListOf()

    if (isSelected)
        service.id?.let { serviceId -> services.add(serviceId) }
    else
        services.remove(service.id)

    TempData.currentMaster.services = services

    updateMaster(TempData.currentMaster)
}