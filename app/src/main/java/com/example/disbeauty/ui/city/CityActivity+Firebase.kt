package com.example.disbeauty.ui.city

import com.example.disbeauty.R
import com.example.disbeauty.data.firebase.FirebaseConstants
import com.example.disbeauty.data.firebase.FirebaseInstances
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot

fun CityActivity.getCities(onLoad: (Task<QuerySnapshot>) -> Unit) {
    FirebaseInstances.firestore
        .collection(FirebaseConstants.citiesCollection)
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

fun CityActivity.setCity(city: String, onLoad: () -> Unit) {
    FirebaseInstances.firestore
        .collection(FirebaseConstants.mastersCollection)
        .document(FirebaseInstances.auth.currentUser?.uid ?: "")
        .update(FirebaseConstants.cityField, city)
        .addOnCompleteListener { task ->
            if (task.isSuccessful)
                onLoad()
            else
                showSnackBarMessage(
                    task.exception?.localizedMessage
                        ?: getString(R.string.stringUnknownError)
                )
        }
}