package com.example.gestordeinventario.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gestordeinventario.ui.home.HomeScreen
import com.example.gestordeinventario.ui.login.LoginScreen
import com.example.gestordeinventario.ui.login.LoginViewModel
import com.example.gestordeinventario.ui.registration.RegistrationScreen
import com.example.gestordeinventario.ui.registration.RegistrationViewModel
import com.example.gestordeinventario.ui.students_list.Student
import com.example.gestordeinventario.ui.students_list.StudentsListScreen
import com.example.gestordeinventario.ui.students_list.StudentsListViewModel

@Composable
fun NavigationWrapper() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = StudentsList){
        composable<Login>{
            LoginScreen(
                LoginViewModel(),
                        {navController.navigate(Home)},
                        {navController.navigate(Registration)}
                )
        }

        composable<Registration>{
            RegistrationScreen(RegistrationViewModel()){navController.navigate(Home)}
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

        composable<StudentsList> {
            val viewModel = StudentsListViewModel()

            for (i in 1..99) {
                viewModel.addStudent(Student("Alumno $i", i*100000, i, i*2))
            }
            StudentsListScreen(viewModel) {
                navController.navigate(Login) {
                    popUpTo(Login) {
                        inclusive = true
                    }
                }
            }
        }
    }
}