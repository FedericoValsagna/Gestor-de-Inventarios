package com.example.gestordeinventario.model

import android.util.Log
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.security.InvalidParameterException

class Authenticator {
    suspend fun login(email: String?, password: String?): LoginResponse {
        if (email == null || password == null) {
            throw InvalidParameterException()
        }
        try {
            val task = Firebase.auth.signInWithEmailAndPassword(email, password).await()
            Log.d("AUTHENTICATION", "Task value: $task")
            return LoginResponse.SUCESSFUL
        } catch (error: FirebaseAuthException) {
            when(error) {
                is FirebaseAuthInvalidUserException -> return LoginResponse.INVALID_EMAIL
                is FirebaseAuthInvalidCredentialsException -> return LoginResponse.INVALID_PASSWORD
                else -> return LoginResponse.OTHER_ERROR
            }
        }
    }
}

enum class LoginResponse{
    SUCESSFUL, INVALID_EMAIL, INVALID_PASSWORD, OTHER_ERROR
}