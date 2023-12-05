package com.example.unshelf.model.authentication

import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.Firebase
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth.*
import com.google.firebase.firestore.firestore

class LoginAuthenticationManager {
    val firebaseAuth = getInstance()

    fun loginUser(
        email: String,
        password: String,
        onCompleteListener: OnCompleteListener<AuthResult?>
    ) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(onCompleteListener)
    }
}


