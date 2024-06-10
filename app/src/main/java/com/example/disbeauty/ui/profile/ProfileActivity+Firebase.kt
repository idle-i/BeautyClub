package com.example.disbeauty.ui.profile

import com.example.disbeauty.R
import com.example.disbeauty.data.dto.Master
import com.example.disbeauty.data.firebase.FirebaseConstants
import com.example.disbeauty.data.firebase.FirebaseInstances

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