package com.example.disbeauty.ui.datetime

import com.example.disbeauty.R
import com.example.disbeauty.data.TempData
import com.example.disbeauty.data.firebase.FirebaseConstants
import com.example.disbeauty.data.firebase.FirebaseInstances
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot

fun DateTimeActivity.addAppointment(onAdded: () -> Unit) {
    FirebaseInstances.firestore
        .collection(FirebaseConstants.appointmentsCollection)
        .add(TempData.currentOrder)
        .addOnCompleteListener { task ->
            if (task.isSuccessful)
                onAdded()
            else
                showSnackBarMessage(
                    task.exception?.localizedMessage
                        ?: getString(R.string.stringUnknownError)
                )
        }
}

fun DateTimeActivity.getAppointments(
    startTime: Long,
    endTime: Long,
    onLoad: (task: Task<QuerySnapshot>) -> Unit
) {
    FirebaseInstances.firestore
        .collection(FirebaseConstants.appointmentsCollection)
        .whereGreaterThanOrEqualTo(FirebaseConstants.timeField, startTime)
        .whereLessThanOrEqualTo(FirebaseConstants.timeField, endTime)
        .whereEqualTo(FirebaseConstants.masterIdField, TempData.currentOrder.masterId)
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

fun DateTimeActivity.getMaster(onLoad: (task: Task<DocumentSnapshot>) -> Unit) {
    FirebaseInstances.firestore
        .collection(FirebaseConstants.mastersCollection)
        .document(TempData.currentOrder.masterId)
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