package com.example.gestordeinventario.ui.login

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gestordeinventario.core.navigation.ScreensNavigation
import com.example.gestordeinventario.model.Authenticator
import com.example.gestordeinventario.model.LoginResponse
import kotlinx.coroutines.delay

class LoginViewModel : ViewModel() {
    private val _email = MutableLiveData<String>()
    val email : LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password : LiveData<String> = _password

    private val _loginEnable = MutableLiveData<Boolean>()
    val loginEnable : LiveData<Boolean> = _loginEnable

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    fun onLoginChanged(email: String, password: String){
        _email.value = email
        _password.value = password
        _loginEnable.value = isValidEmail(email) && isValidPassword(password)
    }

    private fun isValidPassword(password: String): Boolean {
        return (password.length > 6)
    }

    private fun isValidEmail(email: String): Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()
    suspend fun onLoginSelected(screensNavigation: ScreensNavigation) {
        _isLoading.value = true
        if (_email.value != null && _password.value != null) {
            when(Authenticator().login(_email.value, _password.value)) {
                LoginResponse.SUCESSFUL -> {
                    _isLoading.value = false
                    screensNavigation.navigateToHome()
                }
                LoginResponse.INVALID_EMAIL -> {
                    _isLoading.value = false
                }
                LoginResponse.INVALID_PASSWORD -> {
                    _isLoading.value = false
                }
                LoginResponse.OTHER_ERROR -> {
                    _isLoading.value = false
                }
            }
        }
        // delay(4000)
        // _isLoading.value = false
    }
}