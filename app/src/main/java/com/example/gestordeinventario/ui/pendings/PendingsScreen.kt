package com.example.gestordeinventario.ui.pendings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Button
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
import com.example.gestordeinventario.model.PendingElement
import com.example.gestordeinventario.ui.common.LogoutButton
import com.example.gestordeinventario.ui.students_list.PendingsViewModel
import com.example.gestordeinventario.model.Student
import com.example.gestordeinventario.ui.common.TableCell

@Composable
fun PendingsScreen(viewModel: PendingsViewModel, screensNavigation: ScreensNavigation){
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp
    val student: Student by viewModel.student.observeAsState(initial= Student("", "", emptyList(), ""))

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp) ) {
        PendingsHeader(Modifier.align(Alignment.CenterHorizontally), student.name)
        Spacer(modifier = Modifier.padding(2.dp))
        HorizontalDivider()
        Box(Modifier.height(screenHeight*0.8f)){
            Pendings(student.pendingDevolutions, modifier = Modifier)
        }
        Spacer(modifier = Modifier.padding(2.dp))
        HorizontalDivider()
        LogoutButton(modifier = Modifier.align(Alignment.Start)){screensNavigation.restart()}
    }
}

@Composable
fun PendingsHeader(modifier: Modifier, studentName: String) {
    Text(
        text = "Pendientes $studentName",
        fontSize = 24.sp,
        modifier = modifier.padding(vertical = 16.dp)
    )
}

@Composable
fun Pendings(pendingDevolutions: List<PendingElement>, modifier: Modifier) {
    LazyColumn (
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            Row(Modifier.background(Color.Gray)) {
                TableCell(text = "Elemento", weight = 1f, modifier = modifier)
                TableCell(text = "Cantidad", weight = 1f, modifier = modifier)
                TableCell(text = "Vencimiento", weight = 1f, modifier = modifier)
            }
        }
        items(pendingDevolutions) {element ->
            Row (modifier = modifier.fillMaxWidth()) {
                TableCell(text = element.element.name, weight = 1f, modifier = modifier)
                TableCell(text = element.quantity.toString(), weight = 1f, modifier = modifier)
                TableCell(text = element.devolutionDate.toString(), weight = 1f, modifier = modifier)
            }
        }
    }
}
