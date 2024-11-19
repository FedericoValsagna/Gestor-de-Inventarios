package com.example.gestordeinventario.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.gestordeinventario.ui.home.HomeScreen
import com.example.gestordeinventario.ui.lendings.LendingsScreen
import com.example.gestordeinventario.ui.login.LoginScreen
import com.example.gestordeinventario.ui.login.LoginViewModel
import com.example.gestordeinventario.ui.pendings.PendingsScreen
import com.example.gestordeinventario.ui.registration.RegistrationScreen
import com.example.gestordeinventario.ui.registration.RegistrationViewModel
import com.example.gestordeinventario.ui.students_list.LendingsViewModel
import com.example.gestordeinventario.ui.students_list.PendingsViewModel
import com.example.gestordeinventario.model.Student
import com.example.gestordeinventario.ui.students_list.StudentsListScreen
import com.example.gestordeinventario.ui.students_list.StudentsListViewModel

@Composable
fun NavigationWrapper() {
    val navController = rememberNavController()
    val screensNavigation = ScreensNavigation(navController)

    NavHost(navController = navController, startDestination = Login){
        composable<Login>{
            LoginScreen(LoginViewModel(), screensNavigation)
        }

        composable<Registration>{
            RegistrationScreen(RegistrationViewModel(), screensNavigation)
        }

        composable<Home>{
            HomeScreen(accessPrivileges = "admin", screensNavigation)
        }

        composable<StudentsList> {
            val viewModel = StudentsListViewModel()
            StudentsListScreen(viewModel, screensNavigation)
        }

        composable<Lendings> {
            val lendings : Lendings = it.toRoute()

            LendingsScreen(viewModel = LendingsViewModel(lendings.studentName), screensNavigation)
        }

        composable<Pendings> {
            val pendings : Pendings = it.toRoute()

            PendingsScreen(viewModel = PendingsViewModel(pendings.studentName), screensNavigation)
        }
    }
}