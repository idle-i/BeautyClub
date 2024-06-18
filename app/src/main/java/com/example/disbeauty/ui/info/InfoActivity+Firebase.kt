package com.example.disbeauty.ui.info

import com.example.disbeauty.R
import com.example.disbeauty.data.dto.Order
import com.example.disbeauty.data.dto.Review
import com.example.disbeauty.data.firebase.FirebaseConstants
import com.example.disbeauty.data.firebase.FirebaseInstances

fun InfoActivity.getOrder(orderId: String, onLoad: (Order) -> Unit) {
    FirebaseInstances.firestore
        .collection(FirebaseConstants.appointmentsCollection)
        .document(orderId)
        .get()
        .addOnCompleteListener {
            if (it.isSuccessful) {
                val order = it.result.toObject(Order::class.java) ?: Order()
                onLoad(order)
            } else {
                showSnackBarMessage(
                    it.exception?.localizedMessage
                        ?: getString(R.string.stringUnknownError)
                )
            }
        }
}

fun InfoActivity.cancelOrder(orderId: String, onLoad: () -> Unit) {
    FirebaseInstances.firestore
        .collection(FirebaseConstants.appointmentsCollection)
        .document(orderId)
        .update(FirebaseConstants.canceledField, true)
        .addOnCompleteListener {
            if (it.isSuccessful) {
                onLoad()
            } else {
                showSnackBarMessage(
                    it.exception?.localizedMessage
                        ?: getString(R.string.stringUnknownError)
                )
            }
        }
}

fun InfoActivity.getReviews(orderId: String, onLoad: (List<Review>) -> Unit) {
    FirebaseInstances.firestore
        .collection(FirebaseConstants.reviewCollection)
        .whereEqualTo(FirebaseConstants.userIdField, FirebaseInstances.auth.currentUser?.uid)
        .whereEqualTo(FirebaseConstants.orderIdField, orderId)
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

fun InfoActivity.sendReview(review: Review, onLoad: () -> Unit) {
    FirebaseInstances.firestore
        .collection(FirebaseConstants.reviewCollection)
        .add(review)
        .addOnCompleteListener {
            if (it.isSuccessful) {
                onLoad()
            } else {
                showSnackBarMessage(
                    it.exception?.localizedMessage
                        ?: getString(R.string.stringUnknownError)
                )
            }
        }
}