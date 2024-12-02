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
object Devolutions

@Serializable
data class Lendings(val studentName: String)

@Serializable
data class Pendings(val studentName: String)

@Serializable
object Providers

@Serializable
data class Replenish(val providerName: String)

@Serializable
data class StudentPendings(val padron: String)

@Serializable
object StudentElements

class ScreensNavigation(val navController: NavController) {
    fun navigateToLogin() = navController.navigate(Login){launchSingleTop = true}
    fun navigateToHome(padron: String, isProfessor: Boolean) = navController.navigate(Home(padron, isProfessor)){launchSingleTop = true}
    fun navigateToRegistration() = navController.navigate(Registration){launchSingleTop = true}
    fun navigateToStudentsList() = navController.navigate(StudentsList){launchSingleTop = true}
    fun navigateToElements() = navController.navigate(Elements){launchSingleTop = true}
    fun navigateToDevolutions() = navController.navigate(Devolutions){launchSingleTop = true}
    fun navigateToLendings(studentName: String) = navController.navigate(Lendings(studentName)){launchSingleTop = true}
    fun navigateToPendings(studentName: String) = navController.navigate(Pendings(studentName)){launchSingleTop = true}
    fun restart() = navController.navigate(Login) { popUpTo(Login) { inclusive = true } }
    fun navigateToProvidersList() = navController.navigate(Providers)
    fun navigateToReplenish(providerName: String) = navController.navigate(Replenish(providerName))
    fun navigateToStudentPendings(padron: String) = navController.navigate(StudentPendings(padron))
    fun navigateToStudentElements() = navController.navigate(StudentElements)
}