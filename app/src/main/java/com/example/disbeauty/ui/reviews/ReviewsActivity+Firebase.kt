package com.example.disbeauty.ui.reviews

import com.example.disbeauty.R
import com.example.disbeauty.data.dto.Review
import com.example.disbeauty.data.firebase.FirebaseConstants
import com.example.disbeauty.data.firebase.FirebaseInstances

fun ReviewsActivity.getReviews(masterId: String, onLoad: (List<Review>) -> Unit) {
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