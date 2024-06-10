package com.example.disbeauty.ui.start

import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import com.example.disbeauty.R
import com.example.disbeauty.data.dto.Master
import com.example.disbeauty.data.firebase.FirebaseConstants
import com.example.disbeauty.data.firebase.FirebaseInstances
import com.example.disbeauty.ui.appointments.AppointmentsActivity
import com.example.disbeauty.ui.main.MainActivity

fun StartActivity.addOnClickListeners() {
    binding.confirmButton.setOnClickListener {
        if (currentLayoutType == LayoutType.SIGN_IN)
            signIn {
                FirebaseInstances.firestore
                    .collection(FirebaseConstants.mastersCollection)
                    .whereEqualTo(
                        FirebaseConstants.idField,
                        FirebaseInstances.auth.currentUser?.uid ?: ""
                    )
                    .get()
                    .addOnCompleteListener { task ->
                        if (task.result.toObjects(Master::class.java).isNotEmpty())
                            showAppointmentsActivity()
                        else
                            showMainActivity()
                    }
            }
        else {
            val asMaster = binding.signUpLayout.masterCheckbox.isChecked

            signUp(asMaster) {
                if (asMaster)
                    showAppointmentsActivity()
                else
                    showMainActivity()
            }
        }
    }
}

fun StartActivity.showViews() {
    val fadeInAnimation: Animation = AnimationUtils.loadAnimation(
        this,
        R.anim.fade_in
    ).apply { duration = 1000 }

    listOf(binding.titleLabel, binding.confirmButton, binding.signInLayout.root).forEach {
        it.startAnimation(fadeInAnimation)
    }
}

fun StartActivity.setupSignInLayout() {
    binding.signInLayout.apply {
        signUpButton.setOnClickListener {
            currentLayoutType = LayoutType.SIGN_UP

            changeLayout()
        }
    }
}

fun StartActivity.setupSignUpLayout() {
    binding.signUpLayout.apply {
        signInButton.setOnClickListener {
            currentLayoutType = LayoutType.SIGN_IN

            changeLayout()
        }
    }
}

private fun StartActivity.changeLayout() {
    val enterView: View =
        if (currentLayoutType == LayoutType.SIGN_IN) binding.signInLayout.root else binding.signUpLayout.root
    val exitView: View =
        if (currentLayoutType == LayoutType.SIGN_IN) binding.signUpLayout.root else binding.signInLayout.root

    val enterAnimation: Animation = AnimationUtils.loadAnimation(
        this,
        if (currentLayoutType == LayoutType.SIGN_IN) R.anim.slide_in_from_left else R.anim.slide_in_from_right
    )

    val exitAnimation: Animation = AnimationUtils.loadAnimation(
        this,
        if (currentLayoutType == LayoutType.SIGN_IN) R.anim.slide_out_to_right else R.anim.slide_out_to_left
    ).apply {
        setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {}

            override fun onAnimationEnd(p0: Animation?) {
                exitView.clearAnimation()

                exitView.visibility = View.GONE
            }

            override fun onAnimationRepeat(p0: Animation?) {}
        })
    }

    enterView.apply {
        visibility = View.VISIBLE

        startAnimation(enterAnimation)
    }

    exitView.startAnimation(exitAnimation)

    changeLabels()
}

private fun StartActivity.changeLabels() {
    val labels: List<TextView> = listOf(binding.titleLabel, binding.confirmButton)

    val fadeInAnimation: Animation = AnimationUtils.loadAnimation(
        this,
        R.anim.fade_in
    )

    val fadeOutAnimation: Animation = AnimationUtils.loadAnimation(
        this,
        R.anim.fade_out
    ).apply {
        setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {}

            override fun onAnimationEnd(p0: Animation?) {
                labels.forEach {
                    it.text = getString(
                        if (currentLayoutType == LayoutType.SIGN_IN) R.string.stringSignIn
                        else R.string.stringSignUp
                    )

                    it.startAnimation(fadeInAnimation)
                }
            }

            override fun onAnimationRepeat(p0: Animation?) {}
        })
    }

    labels.forEach { it.startAnimation(fadeOutAnimation) }
}

private fun StartActivity.showMainActivity() {
    startActivityWithFinish(MainActivity::class.java)
}

private fun StartActivity.showAppointmentsActivity() {
    startActivityWithFinish(AppointmentsActivity::class.java)

    overridePendingTransition(
        R.anim.fade_in,
        R.anim.fade_out
    )
}