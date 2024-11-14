package com.example.gestordeinventario.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gestordeinventario.ui.home.ui.HomeScreen
import com.example.gestordeinventario.ui.login.ui.LoginScreen
import com.example.gestordeinventario.ui.login.ui.LoginViewModel
import com.example.gestordeinventario.ui.registration.ui.RegistrationScreen
import com.example.gestordeinventario.ui.registration.ui.RegistrationViewModel

@Composable
fun NavigationWrapper() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Login){
        composable<Login>{
            LoginScreen(LoginViewModel(),
                        {navController.navigate(Home)},
                        {navController.navigate(Registration)}
                )
        }

        composable<Home>{
            HomeScreen(accessPrivileges = "admin") {
                navController.navigate(Login) {
                    popUpTo(Login) {
                        inclusive = true
                    }
                }
            }
        }

        composable<Registration>{
            RegistrationScreen(RegistrationViewModel()){navController.navigate(Home)}
        }
    }
}