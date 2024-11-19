package com.example.gestordeinventario.ui.lendings

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import com.example.gestordeinventario.ui.common.LogoutButton
import com.example.gestordeinventario.ui.students_list.LendingsViewModel
import com.example.gestordeinventario.model.Student

@Composable
fun LendingsScreen(viewModel: LendingsViewModel, studentName: String, screensNavigation: ScreensNavigation){
    val studentsList : List<Student> by viewModel.studentsList.observeAsState(initial = emptyList())
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp) ) {
        LendingsHeader(Modifier.align(Alignment.CenterHorizontally), studentName)
        Spacer(modifier = Modifier.padding(2.dp))
        HorizontalDivider()
        Box(Modifier.height(screenHeight*0.8f)){
            Lendings(studentsList, modifier = Modifier)
        }
        Spacer(modifier = Modifier.padding(2.dp))
        HorizontalDivider()
        LogoutButton(modifier = Modifier.align(Alignment.Start)){screensNavigation.restart()}
    }
}

@Composable
fun LendingsHeader(modifier: Modifier, studentName: String) {
    Text(
        text = "Prestaciones $studentName",
        fontSize = 24.sp,
        modifier = modifier.padding(vertical = 16.dp)
    )
}

@Composable
fun Lendings(students: List<Student>, modifier: Modifier) {
    LazyColumn (
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            Row(Modifier.background(Color.Gray)) {
                LendingsTableCell(text = "Elemento", weight = 1f, modifier = modifier)
                LendingsTableCell(text = "Cantidad", weight = 1f, modifier = modifier)
                LendingsTableCell(text = "Tiempo", weight = 1f, modifier = modifier)
            }
        }
        items(students) {student ->
            Row (modifier = modifier.fillMaxWidth()) {
                LendingsTableCell(text = student.name, weight = 1f, modifier = modifier)
                LendingsTableCell(text = student.padron.toString(), weight = 1f, modifier = modifier)
                LendingsTableCell(text = student.pendingDevolutions.toString(), weight = 1f, modifier = modifier)
            }
        }
    }
}

@Composable
fun RowScope.LendingsTableCell(
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
