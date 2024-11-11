package com.example.gestordeinventario.ui.registration.ui

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay

class RegistrationViewModel : ViewModel() {
    private val _email = MutableLiveData<String>()
    val email : LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password : LiveData<String> = _password

    private val _registrationEnable = MutableLiveData<Boolean>()
    val registrationEnable : LiveData<Boolean> = _registrationEnable

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    fun onRegistrationChanged(email: String, password: String){
        _email.value = email
        _password.value = password
        _registrationEnable.value = isValidEmail(email) && isValidPassword(password)
    }

    private fun isValidPassword(password: String): Boolean {
        return (password.length > 6)
    }

    private fun isValidEmail(email: String): Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()
    suspend fun onRegistrationSelected() {
        _isLoading.value = true
        delay(4000)
        _isLoading.value = false
    }
}