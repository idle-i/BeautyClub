package com.example.disbeauty.ui.datetime

import android.app.DatePickerDialog
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.disbeauty.R
import com.example.disbeauty.adapters.TimeAdapter
import com.example.disbeauty.data.TempData
import com.example.disbeauty.data.dto.Master
import com.example.disbeauty.data.dto.Order
import com.example.disbeauty.ui.result.ResultActivity
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun DateTimeActivity.addOnClickListeners() {
    binding.dateLayout.setOnClickListener {
        showDatePickerDialog()
    }

    binding.confirmButton.setOnClickListener {
        when {
            !selectedDateTime.isSet(Calendar.DAY_OF_MONTH) ->
                showSnackBarMessage(getString(R.string.stringSelectDate))

            !selectedDateTime.isSet(Calendar.HOUR_OF_DAY) ->
                showSnackBarMessage(getString(R.string.stringSelectTime))

            selectedDateTime.timeInMillis < Calendar.getInstance().timeInMillis ->
                showSnackBarMessage(getString(R.string.stringSelectCorrectDateTime))

            else -> {
                TempData.currentOrder.time = selectedDateTime.timeInMillis

                addAppointment {
                    showResultActivity()
                }
            }
        }
    }
}

fun DateTimeActivity.showViews() {
    val fadeInAnimation: Animation = AnimationUtils.loadAnimation(
        this,
        R.anim.fade_in
    ).apply { duration = 1000 }

    val fadeInSlideInAnimation: Animation = AnimationUtils.loadAnimation(
        this,
        R.anim.fade_in_slide_in_from_bottom
    )

    listOf(binding.titleLabel, binding.subTitleLabel).forEach {
        it.startAnimation(fadeInAnimation)
    }

    binding.bottomLayout.startAnimation(fadeInSlideInAnimation)
}

private fun DateTimeActivity.showDatePickerDialog() {
    DatePickerDialog(this).apply {
        setOnDateSetListener { _, year, month, day ->
            selectedDateTime.set(year, month, day)

            binding.selectedDateLabel.text =
                SimpleDateFormat(
                    "dd MMM yyyy",
                    Locale.getDefault()
                ).format(selectedDateTime.time)

            showTimeSelect()
        }

        show()
    }
}

private fun DateTimeActivity.showTimeSelect() {
    val calendar: Calendar = selectedDateTime

    getMaster { masterTask ->
        val master = masterTask.result.toObject(Master::class.java) ?: Master()

        val startHour = master.workingStartHour ?: 9
        val endHour = master.workingEndHour ?: 21

        calendar.set(Calendar.HOUR_OF_DAY, startHour)
        val startTime: Long = calendar.timeInMillis

        calendar.set(Calendar.HOUR_OF_DAY, endHour)
        val endTime: Long = calendar.timeInMillis

        binding.timeProgressBar.visibility = View.VISIBLE
        binding.timeRecyclerView.visibility = View.GONE

        getAppointments(startTime, endTime) { appointments ->
            binding.timeProgressBar.visibility = View.GONE

            binding.timeRecyclerView.apply {
                visibility = View.VISIBLE

                layoutManager = LinearLayoutManager(
                    this@showTimeSelect,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )

                adapter = TimeAdapter(
                    this@showTimeSelect,
                    startHour, endHour,
                    appointments.result
                        .toObjects(Order::class.java)
                        .filter { it.canceled == false }
                        .map { order -> order.time },
                    calendar
                ) { selected ->
                    selectedDateTime.set(Calendar.HOUR_OF_DAY, selected)
                }
            }
        }
    }
}

private fun DateTimeActivity.showResultActivity() {
    startActivity(ResultActivity::class.java)
}