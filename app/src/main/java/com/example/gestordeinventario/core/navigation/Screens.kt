package com.example.gestordeinventario.core.navigation

import androidx.navigation.NavController
import kotlinx.serialization.Serializable

@Serializable
object Login

@Serializable
data class Home(val padron: String, val isProfessor: Boolean)

@Serializable
object Registration

@Serializable
object StudentsList

@Serializable
object Elements

@Serializable
data class Lendings(val studentName: String)

@Serializable
data class Pendings(val studentName: String)

class ScreensNavigation(val navController: NavController) {
    fun navigateToLogin() = navController.navigate(Login)
    fun navigateToHome(padron: String, isProfessor: Boolean) = navController.navigate(Home(padron, isProfessor))
    fun navigateToRegistration() = navController.navigate(Registration)
    fun navigateToStudentsList() = navController.navigate(StudentsList)
    fun navigateToElements() = navController.navigate(Elements)
    fun navigateToLendings(studentName: String) = navController.navigate(Lendings(studentName))
    fun navigateToPendings(studentName: String) = navController.navigate(Pendings(studentName))
    fun restart() = navController.navigate(Login) { popUpTo(Login) { inclusive = true } }
}