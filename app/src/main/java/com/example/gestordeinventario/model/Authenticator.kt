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
            val response = LoginResponse.SUCCESSFUL
            response.id = task.user?.uid
            return response
        } catch (error: FirebaseAuthException) {
            when(error) {
                is FirebaseAuthInvalidUserException -> return LoginResponse.INVALID_EMAIL
                is FirebaseAuthInvalidCredentialsException -> return LoginResponse.INVALID_PASSWORD
                else -> return LoginResponse.OTHER_ERROR
            }
        }
    }
    suspend fun signIn(email: String?, password: String?): String?{
        if (email == null || password == null) {
            throw InvalidParameterException()
        }
        val result = Firebase.auth.createUserWithEmailAndPassword(email, password).await()
        val user = result.user
        val id = user?.uid
        return id
    }
}


enum class LoginResponse(var id: String?){
    SUCCESSFUL(id=""), INVALID_EMAIL(id= null), INVALID_PASSWORD(id=null), OTHER_ERROR(id=null)
}