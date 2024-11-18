package com.example.gestordeinventario.core.navigation

import androidx.navigation.NavController
import kotlinx.serialization.Serializable

@Serializable
object Login

@Serializable
object Home

@Serializable
object Registration

@Serializable
object StudentsList

class ScreensNavigation(val navController: NavController) {
    fun NavigateToLogin() = navController.navigate(Login)
    fun NavigateToHome() = navController.navigate(Home)
    fun NavigateToRegistration() = navController.navigate(Registration)
    fun NavigateToStudentsList() = navController.navigate(StudentsList)
    fun Restart() = navController.navigate(Login) { popUpTo(Login) { inclusive = true } }
}