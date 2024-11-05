package com.example.gestordeinventario.model

import android.util.Log
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.security.InvalidParameterException

class Authenticator {
    suspend fun login(email: String?, password: String?): AuthResult? {
        if (email == null || password == null) {
            throw InvalidParameterException()
        }
        try {
            val task = Firebase.auth.signInWithEmailAndPassword(email, password).await()
            Log.d("AUTHENTICATION", "Task value: $task")
            return task
        } catch (error: FirebaseAuthException) {
            return null
        }
    }
}