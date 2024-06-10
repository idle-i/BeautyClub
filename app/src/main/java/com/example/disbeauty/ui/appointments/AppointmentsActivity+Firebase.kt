package com.example.disbeauty.ui.appointments

import com.example.disbeauty.R
import com.example.disbeauty.data.firebase.FirebaseConstants
import com.example.disbeauty.data.firebase.FirebaseInstances
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot

fun AppointmentsActivity.getHistory(onLoad: (Task<QuerySnapshot>) -> Unit) {
    FirebaseInstances.firestore
        .collection(FirebaseConstants.appointmentsCollection)
        .whereEqualTo(
            FirebaseConstants.masterIdField,
            FirebaseInstances.auth.currentUser?.uid ?: ""
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

fun AppointmentsActivity.getAvatarImage(onLoad: (Task<QuerySnapshot>) -> Unit) {
    FirebaseInstances.firestore
        .collection(FirebaseConstants.mastersCollection)
        .whereEqualTo(
            FirebaseConstants.idField,
            FirebaseInstances.auth.currentUser?.uid ?: ""
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