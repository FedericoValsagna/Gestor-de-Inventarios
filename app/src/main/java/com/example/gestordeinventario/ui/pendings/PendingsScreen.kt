package com.example.gestordeinventario.ui.pendings

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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gestordeinventario.core.navigation.ScreensNavigation
import com.example.gestordeinventario.model.PendingElement
import com.example.gestordeinventario.ui.common.LogoutButton
import com.example.gestordeinventario.model.Student
import com.example.gestordeinventario.ui.common.CheckboxCell
import com.example.gestordeinventario.ui.common.CheckboxInfo
import com.example.gestordeinventario.ui.common.TableCell
import com.example.gestordeinventario.ui.common.getFormatDate

@Composable
fun PendingsScreen(viewModel: PendingsViewModel, screensNavigation: ScreensNavigation){
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val student: Student by viewModel.student.collectAsState()
    val isLoading: Boolean by viewModel.isLoading.collectAsState()

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
            PendingsHeader(Modifier.align(Alignment.CenterHorizontally), student.name)
            Spacer(modifier = Modifier.padding(2.dp))
            HorizontalDivider()
            Box(Modifier.height(screenHeight * 0.7f)) {
                Pendings(
                    viewModel,
                    student.getOngoingPendingElements(),
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Spacer(modifier = Modifier.padding(2.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.padding(8.dp))
            PendingAcceptButton(
                viewModel = viewModel,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) { screensNavigation.navigateToPendings(student.padron) }
            Spacer(modifier = Modifier.padding(8.dp))
            LogoutButton(modifier = Modifier.align(Alignment.Start)) { screensNavigation.restart() }
        }
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
fun Pendings(
    viewModel: PendingsViewModel,
    pendingDevolutions: List<PendingElement>,
    modifier: Modifier
) {
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
                TableCell(text = "Entregado", weight = 1f, modifier = modifier)
            }
        }
        items(pendingDevolutions) { pendingElement ->
            var checkedState: Boolean by remember { mutableStateOf(false) }
            Row (modifier = modifier.fillMaxWidth()) {
                TableCell(text = pendingElement.element.name, weight = 1f, modifier = modifier)
                TableCell(text = pendingElement.quantity.toString(), weight = 1f, modifier = modifier)
                TableCell(text = getFormatDate(pendingElement.devolutionDate), weight = 1f, modifier = modifier)
                CheckboxCell(checkedState = checkedState, weight = 1f, modifier = modifier){
                    viewModel.updateCheckbox(pendingElement.reference.toString(), it); checkedState = it
                }
            }
        }
    }
}

@Composable
fun PendingAcceptButton(
    viewModel: PendingsViewModel,
    modifier: Modifier,
    resetPendingsScreen: () -> Unit
) {
    Button(
        onClick = { viewModel.sumbitDevolutions(); resetPendingsScreen()},
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

