package com.example.gestordeinventario.ui.students_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.gestordeinventario.core.navigation.ScreensNavigation
import com.example.gestordeinventario.model.Student
import com.example.gestordeinventario.ui.common.LogoutButton
import com.example.gestordeinventario.ui.common.TableCell
import com.example.gestordeinventario.ui.common.TableClickableCell

@Composable
fun StudentsListScreen(viewModel: StudentsListViewModel, screensNavigation: ScreensNavigation){
    val studentsList : List<Student> by viewModel.studentsList.observeAsState(initial = emptyList())
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val isLoading: Boolean by viewModel.isLoading.observeAsState(initial = false)

    if(isLoading){
        Box(Modifier.fillMaxSize()){
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        }
    }
    else {
        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            StudentListHeader(Modifier.align(Alignment.CenterHorizontally))
            Spacer(modifier = Modifier.padding(2.dp))
            HorizontalDivider()
            Box(Modifier.height(screenHeight * 0.8f)) {
                StudentList(
                    students = studentsList,
                    modifier = Modifier,
                    screensNavigation = screensNavigation
                )
            }
            Spacer(modifier = Modifier.padding(2.dp))
            HorizontalDivider()
            LogoutButton(modifier = Modifier.align(Alignment.Start)) { screensNavigation.restart() }
        }
    }
}

@Composable
fun StudentListHeader(modifier: Modifier) {
    Text(
        text = "Listado de Alumnos",
        fontSize = 24.sp,
        modifier = modifier.padding(vertical = 16.dp)
    )
}

@Composable
fun StudentList(students: List<Student>, modifier: Modifier, screensNavigation: ScreensNavigation) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            Row(Modifier.background(Color.Gray)) {
                TableCell(text = "Alumno", weight = 1f, modifier = modifier)
                TableCell(text = "Padrón", weight = 1f, modifier = modifier)
                TableCell(text = "Pendientes", weight = 1f, modifier = modifier)
                TableCell(text = "Prestaciones", weight = 1f, modifier = modifier)
            }
        }
        items(students) { student ->
            Row(modifier = modifier.fillMaxWidth()) {
                TableCell(text = student.name, weight = 1f, modifier = modifier)
                TableCell(
                    text = student.padron,
                    weight = 1f,
                    modifier = modifier
                )
                TableClickableCell(
                    text = "Pendientes",
                    weight = 1f,
                    modifier = modifier,
                    navigateToScreen = {screensNavigation.navigateToPendings(student.padron)}
                )
                TableClickableCell(
                    text = "Prestaciones",
                    weight = 1f,
                    modifier = modifier,
                    navigateToScreen = {screensNavigation.navigateToLendings(student.padron)}
                )
            }
        }
    }
}
