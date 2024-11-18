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

@Serializable
data class Lendings(val studentName: String)

@Serializable
data class Pendings(val studentName: String)

class ScreensNavigation(val navController: NavController) {
    fun navigateToLogin() = navController.navigate(Login)
    fun navigateToHome() = navController.navigate(Home)
    fun navigateToRegistration() = navController.navigate(Registration)
    fun navigateToStudentsList() = navController.navigate(StudentsList)
    fun navigateToLendings(studentName: String) = navController.navigate(Lendings(studentName))
    fun navigateToPendings(studentName: String) = navController.navigate(Pendings(studentName))
    fun restart() = navController.navigate(Login) { popUpTo(Login) { inclusive = true } }
}