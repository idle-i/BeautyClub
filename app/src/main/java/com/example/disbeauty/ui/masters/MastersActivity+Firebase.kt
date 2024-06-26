package com.example.disbeauty.ui.masters

import com.example.disbeauty.R
import com.example.disbeauty.data.dto.Review
import com.example.disbeauty.data.firebase.FirebaseConstants
import com.example.disbeauty.data.firebase.FirebaseInstances
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot

fun MastersActivity.getMasters(id: String, cityId: String, onLoad: (Task<QuerySnapshot>) -> Unit) {
    FirebaseInstances.firestore
        .collection(FirebaseConstants.mastersCollection)
        .whereArrayContains(FirebaseConstants.servicesField, id)
        .whereEqualTo(
            FirebaseConstants.cityField,
            cityId
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

fun MastersActivity.getReviews(masterId: String, onLoad: (List<Review>) -> Unit) {
    FirebaseInstances.firestore
        .collection(FirebaseConstants.reviewCollection)
        .whereEqualTo(FirebaseConstants.masterIdField, masterId)
        .get()
        .addOnCompleteListener {
            if (it.isSuccessful) {
                onLoad(it.result.toObjects(Review::class.java))
            } else {
                showSnackBarMessage(
                    it.exception?.localizedMessage
                        ?: getString(R.string.stringUnknownError)
                )
            }
        }
}