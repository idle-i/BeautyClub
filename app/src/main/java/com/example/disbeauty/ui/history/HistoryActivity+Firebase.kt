package com.example.disbeauty.ui.history

import com.example.disbeauty.R
import com.example.disbeauty.data.firebase.FirebaseConstants
import com.example.disbeauty.data.firebase.FirebaseInstances
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot

fun HistoryActivity.getHistory(onLoad: (Task<QuerySnapshot>) -> Unit) {
    FirebaseInstances.firestore
        .collection(FirebaseConstants.appointmentsCollection)
        .whereEqualTo(FirebaseConstants.userIdField, FirebaseInstances.auth.currentUser?.uid ?: "")
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