package com.example.disbeauty.ui.profile

import android.app.TimePickerDialog
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.disbeauty.R
import com.example.disbeauty.data.firebase.FirebaseConstants
import com.example.disbeauty.data.firebase.FirebaseInstances
import com.example.disbeauty.ui.services_change.ServicesChangeActivity


fun ProfileActivity.showViews() {
    val fadeInAnimation: Animation = AnimationUtils.loadAnimation(
        this,
        R.anim.fade_in
    ).apply {
        duration = 1000

        setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {}

            override fun onAnimationEnd(p0: Animation?) {
                getProfile { master ->
                    binding.profileProgressBar.visibility = View.GONE
                    binding.contentView.visibility = View.VISIBLE
                    binding.avatarLayout.visibility = View.INVISIBLE

                    if (!master.avatar.isNullOrEmpty())
                        Glide
                            .with(this@showViews)
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

                    binding.nameLabel.text = master.name
                    binding.emailInput.setText(FirebaseInstances.auth.currentUser?.email ?: "")
                    binding.phoneInput.setText(master.phoneNumber)

                    workingStartTime = listOf(master.workingStartHour ?: 9, master.workingStartMinute ?: 0)
                    workingEndTime = listOf(master.workingEndHour ?: 21, master.workingEndMinute ?: 0)

                    setWorkingTimeLabel()

                    this@showViews.master = master
                }

                addOnClickListeners()
            }

            override fun onAnimationRepeat(p0: Animation?) {}
        })
    }

    binding.mainContainer.startAnimation(fadeInAnimation)
}


fun ProfileActivity.addOnClickListeners() {
    binding.backButton.setOnClickListener {
        finish()
    }

    binding.workingTimeLayout.setOnClickListener {
        showWorkingTimeStartPickerDialog()
    }

    binding.editButton.setOnClickListener {
        isEditing = !isEditing

        editingStateChanged()

        if (!isEditing) {
            updateProfile()
        }
    }

    binding.servicesChange.setOnClickListener {
        startActivity(ServicesChangeActivity::class.java)
    }
}

private fun ProfileActivity.showWorkingTimeStartPickerDialog() {
    TimePickerDialog(
        this,
        { _, hour, minute ->
            showWorkingTimeEndPickerDialog(hour, minute)
        },
        workingStartTime[0],
        workingStartTime[1],
        true
    ).apply {
        setMessage(getString(R.string.stringSelectWorkingTimeStart))

        show()
    }
}

private fun ProfileActivity.showWorkingTimeEndPickerDialog(startHour: Int, startMinute: Int) {
    TimePickerDialog(
        this,
        { _, hour, minute ->
            workingStartTime = listOf(startHour, startMinute)
            workingEndTime = listOf(hour, minute)

            updateProfile()

            setWorkingTimeLabel()
        },
        workingEndTime[0],
        workingEndTime[1],
        true
    ).apply {
        setMessage(getString(R.string.stringSelectWorkingTimeEnd))

        show()
    }
}

private fun ProfileActivity.setWorkingTimeLabel() {
    binding.workingTimeLabel.text = "с ${workingStartTime[0]}:${if (workingStartTime[1] < 10) "0" else ""}${workingStartTime[1]} до с ${workingEndTime[0]}:${if (workingEndTime[1] < 10) "0" else ""}${workingEndTime[1]}"
}

private fun ProfileActivity.updateProfile() {
    val master = this.master

    FirebaseInstances.auth.currentUser?.let {
        it.updateEmail(binding.emailInput.text.toString())
        FirebaseInstances.auth.updateCurrentUser(it)
    }

    master.phoneNumber = binding.phoneInput.text.toString()
    master.workingStartHour = workingStartTime[0]
    master.workingStartMinute = workingStartTime[1]
    master.workingEndHour = workingEndTime[0]
    master.workingEndMinute = workingEndTime[1]

    FirebaseInstances.firestore
        .collection(FirebaseConstants.mastersCollection)
        .document(FirebaseInstances.auth.currentUser?.uid ?: "")
        .set(master)

    this.master = master
}

private fun ProfileActivity.editingStateChanged() {
    binding.emailInput.isEnabled = isEditing
    binding.phoneInput.isEnabled = isEditing

    binding.editButton.setImageDrawable(
        AppCompatResources.getDrawable(
            this,
            if (isEditing)
                R.drawable.ic_check_mark
            else
                R.drawable.ic_settings
        )
    )

    if (isEditing) {
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
        binding.emailInput.requestFocus()
        (getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)
            ?.showSoftInput(binding.emailInput, InputMethodManager.SHOW_IMPLICIT)
    } else {
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
    }
}