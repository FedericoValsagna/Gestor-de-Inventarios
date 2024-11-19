package com.example.gestordeinventario.ui.students_list

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gestordeinventario.core.navigation.ScreensNavigation
import com.example.gestordeinventario.model.Student
import com.example.gestordeinventario.ui.common.LogoutButton

@Composable
fun StudentsListScreen(viewModel: StudentsListViewModel, screensNavigation: ScreensNavigation){
    val studentsList : List<Student> by viewModel.studentsList.observeAsState(initial = emptyList())
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp) ) {
        StudentListHeader(Modifier.align(Alignment.CenterHorizontally))
        Spacer(modifier = Modifier.padding(2.dp))
        HorizontalDivider()
        Box(Modifier.height(screenHeight*0.8f)){
            StudentList(students = studentsList, modifier = Modifier, screensNavigation = screensNavigation)
        }
        Spacer(modifier = Modifier.padding(2.dp))
        HorizontalDivider()
        LogoutButton(modifier = Modifier.align(Alignment.Start)){screensNavigation.restart()}
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
                StudentListTableCell(text = "Alumno", weight = 1f, modifier = modifier)
                StudentListTableCell(text = "Padrón", weight = 1f, modifier = modifier)
                StudentListTableCell(text = "Pendientes", weight = 1f, modifier = modifier)
                StudentListTableCell(text = "Prestaciones", weight = 1f, modifier = modifier)
            }
        }
        items(students) { student ->
            Row(modifier = modifier.fillMaxWidth()) {
                StudentListTableCell(text = student.name, weight = 1f, modifier = modifier)
                StudentListTableCell(
                    text = student.padron.toString(),
                    weight = 1f,
                    modifier = modifier
                )
                StudentListTableClickableCell(
                    text = "Pendientes",
                    weight = 1f,
                    modifier = modifier,
                    navigateToScreen = {screensNavigation.navigateToPendings(student.name)},
                    navigateString = student.name
                )
                StudentListTableClickableCell(
                    text = "Prestaciones",
                    weight = 1f,
                    modifier = modifier,
                    navigateToScreen = {screensNavigation.navigateToLendings(student.name)},
                    navigateString = student.name
                )
            }
        }
    }
}

@Composable
fun RowScope.StudentListTableClickableCell(
    text: String,
    weight: Float,
    modifier: Modifier,
    navigateToScreen : (String) -> Unit,
    navigateString : String
) {
    Text(
        text = text,
        modifier = modifier
            .clickable { navigateToScreen(navigateString) }
            .border(1.dp, Color.White)
            .weight(weight)
            .padding(8.dp)
            .size(52.dp)
            .align(Alignment.CenterVertically),
        color = Color(0xFF4EA8E9)
    )
}

@Composable
fun RowScope.StudentListTableCell(
    text: String,
    weight: Float,
    modifier: Modifier
) {
    Text(
        text = text,
        modifier = modifier
            .border(1.dp, Color.White)
            .weight(weight)
            .padding(8.dp)
            .size(52.dp)
            .align(Alignment.CenterVertically)
    )
}
