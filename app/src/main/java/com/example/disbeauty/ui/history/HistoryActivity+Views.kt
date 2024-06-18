package com.example.disbeauty.ui.history

import android.content.Intent
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.disbeauty.R
import com.example.disbeauty.adapters.HistoryAdapter
import com.example.disbeauty.data.dto.Order
import com.example.disbeauty.ui.info.InfoActivity
import com.example.disbeauty.utils.dpToPx
import java.util.Calendar

fun HistoryActivity.setupViews() {
    binding.historyRecyclerView.apply {
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

fun HistoryActivity.showViews() {
    binding.historyProgressBar.visibility = View.VISIBLE
    binding.historyRecyclerView.adapter = null

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

private fun HistoryActivity.addOnClickListeners() {
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
}

private fun HistoryActivity.changeHistoryType(type: HistoryType) {
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

private fun HistoryActivity.updateRecyclerView(type: HistoryType) {
    val calendar: Calendar = Calendar.getInstance()

    binding.historyRecyclerView.adapter =
        HistoryAdapter(
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
            intent.putExtra("isMaster", false)
            startActivityForResult(intent, 1)

            overridePendingTransition(
                R.anim.fade_in,
                R.anim.fade_out
            )
        }
}

enum class HistoryType {
    UPCOMING, PAST
}