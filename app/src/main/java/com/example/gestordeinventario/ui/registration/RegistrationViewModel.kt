package com.example.gestordeinventario.ui.registration

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gestordeinventario.model.Authenticator
import com.example.gestordeinventario.model.Student
import com.example.gestordeinventario.repository.StudentRepository
import kotlinx.coroutines.delay

class RegistrationViewModel : ViewModel() {
    private val _email = MutableLiveData<String>()
    val email : LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password : LiveData<String> = _password

    private val _name = MutableLiveData<String>()
    val name: LiveData<String> = _name

    private val _padron = MutableLiveData<String>()
    val padron: LiveData<String> = _padron

    private val _registrationEnable = MutableLiveData<Boolean>()
    val registrationEnable : LiveData<Boolean> = _registrationEnable

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    fun onRegistrationChanged(email: String, password: String, name: String, padron: String){
        _email.value = email
        _password.value = password
        _registrationEnable.value = isValidEmail(email) && isValidPassword(password)
        _name.value = name
        _padron.value = padron
    }

    private fun isValidPassword(password: String): Boolean {
        return (password.length > 6)
    }

    private fun isValidEmail(email: String): Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()
    suspend fun onRegistrationSelected() {
        _isLoading.value = true
        val name = _name.value
        val padron = _padron.value
        val authId = Authenticator().signIn(email.value, password.value)
        if (authId != null && name != null && padron != null) {
            val student = Student(name, padron, emptyList(), authId)
            StudentRepository().save(student)
        }
        delay(4000)
        _isLoading.value = false
    }
}