package com.example.gestordeinventario.core.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.gestordeinventario.model.Provider
import com.example.gestordeinventario.ui.devolutions.DevolutionsScreen
import com.example.gestordeinventario.ui.devolutions.DevolutionsViewModel
import com.example.gestordeinventario.ui.home.HomeScreen
import com.example.gestordeinventario.ui.lendings.LendingsScreen
import com.example.gestordeinventario.ui.login.LoginScreen
import com.example.gestordeinventario.ui.login.LoginViewModel
import com.example.gestordeinventario.ui.pendings.PendingsScreen
import com.example.gestordeinventario.ui.registration.RegistrationScreen
import com.example.gestordeinventario.ui.registration.RegistrationViewModel
import com.example.gestordeinventario.ui.lendings.LendingsViewModel
import com.example.gestordeinventario.ui.pendings.PendingsViewModel
import com.example.gestordeinventario.ui.elements.ElementsListScreen
import com.example.gestordeinventario.ui.elements.ElementsViewModel
import com.example.gestordeinventario.ui.providers.ProvidersListScreen
import com.example.gestordeinventario.ui.providers.ProvidersViewModel
import com.example.gestordeinventario.ui.replenish.ReplenishScreen
import com.example.gestordeinventario.ui.replenish.ReplenishViewModel
import com.example.gestordeinventario.ui.student_elements.StudentElementsScreen
import com.example.gestordeinventario.ui.student_elements.StudentElementsViewModel
import com.example.gestordeinventario.ui.student_pendings.StudentPendingsScreen
import com.example.gestordeinventario.ui.student_pendings.StudentPendingsViewModel
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
            val x: Home = it.toRoute()
            HomeScreen(padron= x.padron, isProfessor = x.isProfessor, screensNavigation)
        }

        composable<StudentsList> {
            StudentsListScreen(viewModel = StudentsListViewModel(), screensNavigation)
        }

        composable<Elements>{
            ElementsListScreen(viewModel = ElementsViewModel(), screensNavigation)
        }

        composable<Devolutions>{
            DevolutionsScreen(viewModel = DevolutionsViewModel(), screensNavigation)
        }

        composable<Lendings> {
            val lendings : Lendings = it.toRoute()

            LendingsScreen(viewModel = LendingsViewModel(lendings.studentName), screensNavigation)
        }

        composable<Pendings> {
            val pendings : Pendings = it.toRoute()

            PendingsScreen(viewModel = PendingsViewModel(pendings.studentName), screensNavigation)
        }
        composable<Providers> {
            ProvidersListScreen(viewModel = ProvidersViewModel(), screensNavigation)
        }
        composable<Replenish> {
            val replenish: Replenish = it.toRoute()
            ReplenishScreen(viewModel = ReplenishViewModel(replenish.providerName), screensNavigation)
        }
        composable<StudentPendings> {
            val studentPendings: StudentPendings = it.toRoute()
            StudentPendingsScreen(viewModel = StudentPendingsViewModel(studentPendings.padron), screensNavigation)
        }
        composable<StudentElements> {
            StudentElementsScreen(viewModel = StudentElementsViewModel(), screensNavigation)
        }
    }
}