package com.example.disbeauty.ui.services_change

import com.example.disbeauty.R
import com.example.disbeauty.data.dto.Master
import com.example.disbeauty.data.firebase.FirebaseConstants
import com.example.disbeauty.data.firebase.FirebaseInstances
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot

fun ServicesChangeActivity.getServices(category: String, onLoad: (Task<QuerySnapshot>) -> Unit) {
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

fun ServicesChangeActivity.getCategories(onLoad: (Task<QuerySnapshot>) -> Unit) {
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

fun ServicesChangeActivity.getMaster(onLoad: (Master) -> Unit) {
    FirebaseInstances.firestore
        .collection(FirebaseConstants.mastersCollection)
        .document(FirebaseInstances.auth.currentUser?.uid ?: "")
        .get()
        .addOnCompleteListener { task ->
            if (task.isSuccessful)
                task.result.toObject(Master::class.java)?.let { onLoad(it) }
            else
                showSnackBarMessage(
                    task.exception?.localizedMessage
                        ?: getString(R.string.stringUnknownError)
                )
        }
}

fun ServicesChangeActivity.updateMaster(master: Master) {
    FirebaseInstances.firestore
        .collection(FirebaseConstants.mastersCollection)
        .document(FirebaseInstances.auth.currentUser?.uid ?: "")
        .set(master)
        .addOnCompleteListener { task ->
            if (!task.isSuccessful)
                showSnackBarMessage(
                    task.exception?.localizedMessage
                        ?: getString(R.string.stringUnknownError)
                )
        }
}