package com.example.disbeauty.ui.start

import android.util.Patterns
import com.example.disbeauty.R
import com.example.disbeauty.data.TempData
import com.example.disbeauty.data.dto.Master
import com.example.disbeauty.data.dto.User
import com.example.disbeauty.data.firebase.FirebaseConstants
import com.example.disbeauty.data.firebase.FirebaseInstances

fun StartActivity.signIn(onLoad: () -> Unit) {
    binding.signInLayout.apply {
        val email: String = emailInput.text.toString()
        val password: String = passwordInput.text.toString()

        when {
            email.isEmpty() ->
                emailInput.error = getString(R.string.stringEnterEmail)

            !Patterns.EMAIL_ADDRESS.matcher(email).matches() ->
                emailInput.error = getString(R.string.stringEnterCorrectEmail)

            password.isEmpty() ->
                passwordInput.error = getString(R.string.stringEnterPassword)

            else -> {
                FirebaseInstances.auth
                    .signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful)
                            FirebaseInstances.firestore
                                .collection(FirebaseConstants.usersCollection)
                                .document(FirebaseInstances.auth.currentUser?.uid ?: "")
                                .get()
                                .addOnCompleteListener {
                                    if (it.isSuccessful) {
                                        TempData.currentUser =
                                            it.result.toObject(User::class.java) ?: User()

                                        onLoad()
                                    } else {
                                        showSnackBarMessage(
                                            it.exception?.localizedMessage
                                                ?: getString(R.string.stringUnknownError)
                                        )
                                    }
                                }
                        else {
                            showSnackBarMessage(
                                task.exception?.localizedMessage
                                    ?: getString(R.string.stringUnknownError)
                            )
                        }
                    }
            }
        }
    }
}

fun StartActivity.signUp(asMaster: Boolean, onLoad: () -> Unit) {
    binding.signUpLayout.apply {
        val name: String = nameInput.text.toString()
        val email: String = emailInput.text.toString()
        val phoneNumber: String = phoneInput.text.toString()
        val password: String = passwordInput.text.toString()
        val repeatPassword: String = repeatPasswordInput.text.toString()

        when {
            name.isEmpty() ->
                nameInput.error = getString(R.string.stringEnterName)

            email.isEmpty() ->
                emailInput.error = getString(R.string.stringEnterEmail)

            !Patterns.EMAIL_ADDRESS.matcher(email).matches() ->
                emailInput.error = getString(R.string.stringEnterCorrectEmail)

            phoneNumber.isEmpty() ->
                phoneInput.error = getString(R.string.stringEnterPhoneNumber)

            password.isEmpty() ->
                passwordInput.error = getString(R.string.stringEnterPassword)

            repeatPassword.isEmpty() ->
                repeatPasswordInput.error = getString(R.string.stringEnterRepeatPassword)

            password != repeatPassword ->
                repeatPasswordInput.error = getString(R.string.stringPasswordsDoNotMatch)

            else -> {
                FirebaseInstances.auth
                    .createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            TempData.currentUser.apply {
                                this.name = name
                                this.phoneNumber = phoneNumber
                            }

                            FirebaseInstances.firestore
                                .collection(FirebaseConstants.usersCollection)
                                .document(FirebaseInstances.auth.currentUser?.uid ?: "")
                                .set(TempData.currentUser)
                                .addOnCompleteListener {
                                    if (it.isSuccessful) {
                                        val master = Master(
                                            FirebaseInstances.auth.currentUser?.uid,
                                            TempData.currentUser.name,
                                            null,
                                            null,
                                            TempData.currentUser.phoneNumber
                                        )

                                        FirebaseInstances.firestore
                                            .collection(FirebaseConstants.mastersCollection)
                                            .document(FirebaseInstances.auth.currentUser?.uid ?: "")
                                            .set(master)
                                            .addOnCompleteListener { masterTask ->
                                                if (masterTask.isSuccessful)
                                                    onLoad()
                                                else {
                                                    showSnackBarMessage(
                                                        masterTask.exception?.localizedMessage
                                                            ?: getString(R.string.stringUnknownError)
                                                    )
                                                }
                                            }
                                    } else {
                                        showSnackBarMessage(
                                            it.exception?.localizedMessage
                                                ?: getString(R.string.stringUnknownError)
                                        )
                                    }
                                }
                        } else {
                            showSnackBarMessage(
                                task.exception?.localizedMessage
                                    ?: getString(R.string.stringUnknownError)
                            )
                        }
                    }
            }
        }
    }
}