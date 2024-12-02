package com.example.gestordeinventario.ui.lendings

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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gestordeinventario.core.navigation.ScreensNavigation
import com.example.gestordeinventario.model.PendingElement
import com.example.gestordeinventario.ui.common.LogoutButton
import com.example.gestordeinventario.model.Student
import com.example.gestordeinventario.ui.common.TableCell
import com.example.gestordeinventario.ui.common.TableQuantityCell

@Composable
fun LendingsScreen(viewModel: LendingsViewModel, screensNavigation: ScreensNavigation){
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val student: Student by viewModel.student.collectAsState()

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp) ) {
        LendingsHeader(Modifier.align(Alignment.CenterHorizontally), student.name)
        Spacer(modifier = Modifier.padding(2.dp))
        HorizontalDivider()
        Box(Modifier.height(screenHeight*0.7f)){
            Lendings(viewModel = viewModel, modifier = Modifier)
        }
        Spacer(modifier = Modifier.padding(2.dp))
        HorizontalDivider()
        Spacer(modifier = Modifier.padding(8.dp))
        LendingAcceptButton(viewModel = viewModel, modifier = Modifier.align(Alignment.CenterHorizontally)){screensNavigation.navigateToLendings(student.padron)}
        Spacer(modifier = Modifier.padding(8.dp))
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
fun Lendings(viewModel: LendingsViewModel, modifier: Modifier) {
    val pendingsList: List<PendingElement> by viewModel.pendingElements.collectAsState()

    LazyColumn (
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            Row(Modifier.background(Color.Gray)) {
                TableCell(text = "Elemento", weight = 1f, modifier = modifier)
                TableCell(text = "Cantidad", weight = 1f, modifier = modifier)
                TableCell(text = "Tiempo", weight = 1f, modifier = modifier)
            }
        }
        items(pendingsList) { pending ->
            Row(modifier = modifier.fillMaxWidth()) {
                TableCell(
                    text = pending.element.name,
                    weight = 1f,
                    modifier = modifier)
                TableQuantityCell(
                    weight = 1f,
                    modifier = modifier,
                    quantity = pending.quantity,
                    onIncrement = {viewModel.pendingElementQuantityInc(pending)},
                    onDecrement = {viewModel.pendingElementQuantityDec(pending)}
                )
                TableQuantityCell(
                    weight = 1f,
                    modifier = modifier,
                    quantity = viewModel.differenceInDays(pending.devolutionDate).toInt(),
                    onIncrement = {viewModel.pendingElementDaysInc(pending)},
                    onDecrement = {viewModel.pendingElementDaysDec(pending)}
                )
            }
        }
    }
}

@Composable
fun LendingAcceptButton(viewModel: LendingsViewModel, modifier: Modifier, resetLendingsScreen: () -> Unit) {
    Button(
        onClick = { viewModel.submitPendingElements(); resetLendingsScreen()},
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF2F3DA2),
            disabledContainerColor = Color(0xFF6F76AD),
            contentColor = Color.White,
            disabledContentColor = Color.White
        )
    ) {
        Text(text = "Aceptar")
    }
}
