package com.example.disbeauty.ui.profile

import com.example.disbeauty.R
import com.example.disbeauty.data.dto.City
import com.example.disbeauty.data.dto.Master
import com.example.disbeauty.data.firebase.FirebaseConstants
import com.example.disbeauty.data.firebase.FirebaseInstances
import com.example.disbeauty.ui.appointments.AppointmentsActivity
import com.example.disbeauty.ui.main.MainActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot
import java.util.Calendar

fun ProfileActivity.getProfile(onLoad: (Master) -> Unit) {
    FirebaseInstances.firestore
        .collection(FirebaseConstants.mastersCollection)
        .document(FirebaseInstances.auth.currentUser?.uid ?: "")
        .get()
        .addOnCompleteListener {
            if (it.isSuccessful) {
                val master = it.result.toObject(Master::class.java) ?: Master()

                onLoad(master)
            } else {
                showSnackBarMessage(
                    it.exception?.localizedMessage
                        ?: getString(R.string.stringUnknownError)
                )
            }
        }
}

fun ProfileActivity.getHistory(onLoad: (Task<QuerySnapshot>) -> Unit) {
    FirebaseInstances.firestore
        .collection(FirebaseConstants.appointmentsCollection)
        .whereEqualTo(
            FirebaseConstants.masterIdField,
            FirebaseInstances.auth.currentUser?.uid ?: ""
        )
        .whereGreaterThanOrEqualTo(
            FirebaseConstants.timeField,
            Calendar.getInstance().timeInMillis
        )
        .get()
        .addOnCompleteListener { task ->
            if (task.isSuccessful)
                onLoad(task)
            else
                showSnackBarMessage(
                    task.exception?.localizedMessage
                        ?: getString(R.string.stringUnknownError)
                )
        }
}

fun ProfileActivity.getCity(id: String, onLoad: (City) -> Unit) {
    FirebaseInstances.firestore
        .collection(FirebaseConstants.citiesCollection)
        .document(id)
        .get()
        .addOnCompleteListener { task ->
            if (task.isSuccessful)
                task.result.toObject(City::class.java)?.let { onLoad(it) }
            else
                showSnackBarMessage(
                    task.exception?.localizedMessage
                        ?: getString(R.string.stringUnknownError)
                )
        }
}