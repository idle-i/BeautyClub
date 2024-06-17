package com.example.disbeauty.ui.appointments

import android.content.Intent
import android.graphics.drawable.Drawable
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.disbeauty.R
import com.example.disbeauty.adapters.AppointmentsAdapter
import com.example.disbeauty.data.TempData
import com.example.disbeauty.data.dto.Master
import com.example.disbeauty.data.dto.Order
import com.example.disbeauty.data.firebase.FirebaseInstances
import com.example.disbeauty.ui.info.InfoActivity
import com.example.disbeauty.ui.loading.LoadingActivity
import com.example.disbeauty.ui.profile.ProfileActivity
import com.example.disbeauty.utils.dpToPx
import java.util.Calendar

fun AppointmentsActivity.setupViews() {
    binding.titleLabel.text = getString(
        R.string.stringMasterHello,
        TempData.currentUser.name
    )
    binding.avatarLayout.visibility = View.INVISIBLE

    getAvatarImage {
        val master = it.result.toObjects(Master::class.java).firstOrNull()

        if (master?.avatar != null && master.avatar.isNotEmpty()) {
            Glide
                .with(this)
                .load(master.avatar)
                .addListener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return true
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        binding.avatarLayout.visibility = View.VISIBLE
                        binding.avatarImage.setImageDrawable(resource)

                        return true
                    }
                })
                .into(binding.avatarImage)
        }
    }

    binding.recyclerView.apply {
        layoutManager = LinearLayoutManager(this@setupViews)

        addItemDecoration(
            DividerItemDecoration(
                this@setupViews,
                DividerItemDecoration.VERTICAL
            ).apply {
                setPadding(dpToPx(30), dpToPx(30), dpToPx(30), 0)

                setDrawable(
                    ContextCompat.getDrawable(
                        this@setupViews,
                        R.drawable.ic_separator
                    )!!
                )
            }
        )
    }
}

fun AppointmentsActivity.showViews() {
    binding.historyProgressBar.visibility = View.VISIBLE
    binding.recyclerView.adapter = null

    val fadeInAnimation: Animation = AnimationUtils.loadAnimation(
        this,
        R.anim.fade_in
    ).apply {
        duration = 1000

        setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {}

            override fun onAnimationEnd(p0: Animation?) {
                getHistory {
                    val orders = mutableListOf<Order>()

                    it.result.forEach { task ->
                        val order = task.toObject(Order::class.java)
                        order.id = task.id
                        orders.add(order)
                    }

                    history = orders

                    binding.historyProgressBar.visibility = View.GONE

                    addOnClickListeners()
                    updateRecyclerView(selectedType)
                }
            }

            override fun onAnimationRepeat(p0: Animation?) {}
        })
    }

    binding.mainContainer.startAnimation(fadeInAnimation)
}

private fun AppointmentsActivity.addOnClickListeners() {
    binding.upcomingTab.setOnClickListener {
        if (selectedType != HistoryType.UPCOMING) {
            selectedType = HistoryType.UPCOMING

            changeHistoryType(selectedType)
        }
    }

    binding.pastTab.setOnClickListener {
        if (selectedType != HistoryType.PAST) {
            selectedType = HistoryType.PAST

            changeHistoryType(selectedType)
        }
    }

    binding.logOutButton.setOnClickListener {
        FirebaseInstances.auth.signOut()

        showLoadingActivity()
    }

    binding.avatarLayout.setOnClickListener {
        showProfileActivity()
    }
}

private fun AppointmentsActivity.changeHistoryType(type: HistoryType) {
    binding.upcomingTab.setCompoundDrawablesWithIntrinsicBounds(
        null, null, null,
        AppCompatResources.getDrawable(
            this,
            if (type == HistoryType.UPCOMING)
                R.drawable.shape_selected_tab_indicator
            else
                R.drawable.shape_unselected_tab_indicator
        )
    )

    binding.pastTab.setCompoundDrawablesWithIntrinsicBounds(
        null, null, null,
        AppCompatResources.getDrawable(
            this,
            if (type == HistoryType.PAST)
                R.drawable.shape_selected_tab_indicator
            else
                R.drawable.shape_unselected_tab_indicator
        )
    )

    updateRecyclerView(type)
}

private fun AppointmentsActivity.updateRecyclerView(type: HistoryType) {
    val calendar: Calendar = Calendar.getInstance()

    binding.recyclerView.adapter =
        AppointmentsAdapter(
            this,
            history
                .filter {
                    if (type == HistoryType.UPCOMING)
                        it.time > calendar.timeInMillis
                    else
                        it.time < calendar.timeInMillis
                }
                .sortedBy { it.time }
        ) {
            val intent = Intent(this, InfoActivity::class.java)
            intent.putExtra("orderId", it.id)
            intent.putExtra("isMaster", true)
            startActivityForResult(intent, 1)

            overridePendingTransition(
                R.anim.fade_in,
                R.anim.fade_out
            )
        }
}

private fun AppointmentsActivity.showProfileActivity() {
    startActivity(ProfileActivity::class.java)
}

private fun AppointmentsActivity.showLoadingActivity() {
    startActivityWithFinish(LoadingActivity::class.java, true)
}

enum class HistoryType {
    UPCOMING, PAST
}