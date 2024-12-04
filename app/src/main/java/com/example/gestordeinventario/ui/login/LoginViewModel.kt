package com.example.gestordeinventario.ui.login

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gestordeinventario.core.navigation.ScreensNavigation
import com.example.gestordeinventario.model.Authenticator
import com.example.gestordeinventario.model.LoginResponse
import com.example.gestordeinventario.model.Professor
import com.example.gestordeinventario.repository.ProfessorRepository
import com.example.gestordeinventario.repository.StudentRepository

class LoginViewModel : ViewModel() {
    private val _email = MutableLiveData<String>()
    val email : LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password : LiveData<String> = _password

    private val _loginEnable = MutableLiveData<Boolean>()
    val loginEnable : LiveData<Boolean> = _loginEnable

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    private val _showErrorDialog = MutableLiveData<Boolean>()
    val showErrorDialog: LiveData<Boolean> = _showErrorDialog

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
            when(val response = Authenticator().login(_email.value, _password.value)) {
                LoginResponse.SUCCESSFUL -> {
                    val responseId = response.id
                    Log.i("LOGIN", "LOGIN SUCCESFUL | AuthId: $responseId")
                    if (responseId != null){
                        val student = StudentRepository().getByAuthId(responseId)
                        if (student != null){
                            Log.i("LOGIN", "STUDENT FOUND | Student: $student")
                            screensNavigation.navigateToHome(student.padron, false)
                            _showErrorDialog.value = false
                        } else {
                            val professor = ProfessorRepository().getByAuthId(responseId)
                            if (professor != null){
                                Log.i("LOGIN", "PROFESSOR FOUND | Professor: $professor")
                                screensNavigation.navigateToHome(professor.padron, true)
                                _showErrorDialog.value = true
                            } else {
                                Log.i("LOGIN"  ,"PROFESSOR NOT FOUND")
                                screensNavigation.navigateToHome("106010", true)
                                _showErrorDialog.value = true
                            }
                        }
                    }
                }
                LoginResponse.INVALID_EMAIL -> {
                    _showErrorDialog.value = true
                }
                LoginResponse.INVALID_PASSWORD -> {
                    _showErrorDialog.value = true
                }
                LoginResponse.OTHER_ERROR -> {
                    _showErrorDialog.value = true
                }
            }
        }
         _isLoading.value = false
    }

    fun dismissErrorDialog(){
        _showErrorDialog.value = false
    }
}