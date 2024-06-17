package com.example.disbeauty.ui.main

import com.example.disbeauty.R
import com.example.disbeauty.data.dto.City
import com.example.disbeauty.data.firebase.FirebaseConstants
import com.example.disbeauty.data.firebase.FirebaseInstances
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot

fun MainActivity.getServices(category: String, onLoad: (Task<QuerySnapshot>) -> Unit) {
    FirebaseInstances.firestore
        .collection(FirebaseConstants.servicesCollection)
        .whereEqualTo(FirebaseConstants.categoryField, category)
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

fun MainActivity.getCategories(onLoad: (Task<QuerySnapshot>) -> Unit) {
    FirebaseInstances.firestore
        .collection(FirebaseConstants.categoriesCollection)
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

fun MainActivity.getCity(id: String, onLoad: (City) -> Unit) {
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