package com.example.disbeauty.ui.chat

import com.example.disbeauty.R
import com.example.disbeauty.data.dto.Message
import com.example.disbeauty.data.firebase.FirebaseConstants
import com.example.disbeauty.data.firebase.FirebaseInstances
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot

fun ChatActivity.getMessages(onLoad: (List<Message>) -> Unit) {
    FirebaseInstances.firestore
        .collection(FirebaseConstants.messagesCollection)
        .where(
            Filter.or(
                Filter.and(
                    Filter.equalTo(FirebaseConstants.fromUserIdField, recipientId),
                    Filter.equalTo(FirebaseConstants.toUserIdField, FirebaseInstances.auth.currentUser?.uid ?: "")
                ),
                Filter.and(
                    Filter.equalTo(FirebaseConstants.fromUserIdField, FirebaseInstances.auth.currentUser?.uid ?: ""),
                    Filter.equalTo(FirebaseConstants.toUserIdField, recipientId)
                ),
            )
        )
        .orderBy(FirebaseConstants.timestampField, Query.Direction.DESCENDING)
        .get()
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val messages = task.result.toObjects(Message::class.java)
                onLoad(messages)
            }
            else
                showSnackBarMessage(
                    task.exception?.localizedMessage
                        ?: getString(R.string.stringUnknownError)
                )
        }
}

fun ChatActivity.sendMessage(text: String, onLoad: (Message) -> Unit) {
    val message = Message(
        FirebaseInstances.auth.currentUser?.uid ?: "",
        recipientId,
        text,
        System.currentTimeMillis()
    )

    FirebaseInstances.firestore
        .collection(FirebaseConstants.messagesCollection)
        .add(message)
        .addOnCompleteListener { task ->
            if (task.isSuccessful)
                onLoad(message)
            else
                showSnackBarMessage(
                    task.exception?.localizedMessage
                        ?: getString(R.string.stringUnknownError)
                )
        }
}