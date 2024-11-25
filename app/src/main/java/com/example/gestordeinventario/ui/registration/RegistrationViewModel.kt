package com.example.gestordeinventario.ui.registration

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gestordeinventario.model.Authenticator
import com.example.gestordeinventario.model.PendingElement
import com.example.gestordeinventario.model.Student
import com.example.gestordeinventario.model.User
import com.example.gestordeinventario.repository.StudentRepository
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
        val auth_id = Authenticator().signIn("fedevalsag@hotmail.com", "123456")
        if (auth_id != null) {
            val student = Student("Fede", "106011", emptyList(), auth_id)
            StudentRepository().save(student)
        }
        delay(4000)
        _isLoading.value = false
    }
}