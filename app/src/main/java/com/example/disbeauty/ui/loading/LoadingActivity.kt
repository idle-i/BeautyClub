package com.example.disbeauty.ui.loading

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.disbeauty.base.BaseActivity
import com.example.disbeauty.data.TempData
import com.example.disbeauty.data.dto.Master
import com.example.disbeauty.data.dto.User
import com.example.disbeauty.data.firebase.FirebaseConstants
import com.example.disbeauty.data.firebase.FirebaseInstances
import com.example.disbeauty.databinding.ActivityLoadingBinding
import com.example.disbeauty.ui.appointments.AppointmentsActivity
import com.example.disbeauty.ui.main.MainActivity
import com.example.disbeauty.ui.start.StartActivity

class LoadingActivity : BaseActivity() {

    companion object {
        private const val timerDelay: Long = 2000
    }

    private lateinit var binding: ActivityLoadingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoadingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startTimer()
    }

    private fun startTimer() {
        Handler(Looper.getMainLooper()).postDelayed({
            if (FirebaseInstances.auth.currentUser == null)
                startActivityWithFinish(StartActivity::class.java)
            else
                FirebaseInstances.firestore
                    .collection(FirebaseConstants.mastersCollection)
                    .whereEqualTo(
                        FirebaseConstants.idField,
                        FirebaseInstances.auth.currentUser?.uid ?: ""
                    )
                    .get()
                    .addOnCompleteListener { task ->
                        val master = task.result.toObjects(Master::class.java).firstOrNull()
                        if (master != null) {
                            TempData.currentUser = User(
                                name = master.name,
                                phoneNumber = master.phoneNumber
                            )

                            startActivityWithFinish(AppointmentsActivity::class.java)
                        } else {
                            FirebaseInstances.firestore
                                .collection(FirebaseConstants.usersCollection)
                                .document(FirebaseInstances.auth.currentUser?.uid ?: "")
                                .get()
                                .addOnCompleteListener {
                                    if (it.isSuccessful) {
                                        TempData.currentUser =
                                            it.result.toObject(User::class.java) ?: User()

                                        startActivityWithFinish(MainActivity::class.java)
                                    }
                                }
                        }
                    }
        }, timerDelay)
    }
}