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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gestordeinventario.core.navigation.ScreensNavigation
import com.example.gestordeinventario.model.Element
import com.example.gestordeinventario.model.PendingElement
import com.example.gestordeinventario.ui.common.LogoutButton
import com.example.gestordeinventario.ui.students_list.LendingsViewModel
import com.example.gestordeinventario.model.Student
import com.example.gestordeinventario.ui.common.TableCell
import com.example.gestordeinventario.ui.common.TableQuantityCell

@Composable
fun LendingsScreen(viewModel: LendingsViewModel, screensNavigation: ScreensNavigation){
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp
    val student: Student by viewModel.student.observeAsState(initial= Student("", "", emptyList(), ""))
    val elementsList: List<Element> by viewModel.elementsList.observeAsState(initial= emptyList())

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp) ) {
        LendingsHeader(Modifier.align(Alignment.CenterHorizontally), student.name)
        Spacer(modifier = Modifier.padding(2.dp))
        HorizontalDivider()
        Box(Modifier.height(screenHeight*0.8f)){
            Lendings(elementsList, student.pendingDevolutions, modifier = Modifier)
        }
        Spacer(modifier = Modifier.padding(2.dp))
        HorizontalDivider()
//        Spacer(modifier = Modifier.padding(8.dp))
//        PendingAcceptButton(modifier = Modifier.align(Alignment.CenterHorizontally))
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
fun Lendings(elements: List<Element>, pendings: List<PendingElement>, modifier: Modifier) {
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
        items(elements) { element ->
            Row(modifier = modifier.fillMaxWidth()) {
                val daysInitValue = 0 /* if(pendings.any { it.element.name == element.name }) pendings.first { it.element.name == element.name }.devolutionDate.date else 0 */
                val elementsQuantityInitValue = 9
                var days by remember {mutableIntStateOf(daysInitValue)}
                var elementsQuantity by remember { mutableIntStateOf(if(pendings.any { it.element.name == element.name }) 9 else 0) }

//                if(pendings.any { it.element.name == element.name }){
//                    elementsQuantity = pendings.first{it.element.name == element.name }.quantity
//                }
//                else {
//                    elementsQuantity = 0
//                }

                TableCell(
                    text = element.name,
                    weight = 1f,
                    modifier = modifier)
                TableQuantityCell(
                    weight = 1f,
                    modifier = modifier,
                    quantity = elementsQuantity,
                    onIncrement = {elementsQuantity++},
                    onDecrement = { if(elementsQuantity > 0) {elementsQuantity-- } }
                )
                TableQuantityCell(
                    weight = 1f,
                    modifier = modifier,
                    quantity = days,
                    onIncrement = {days++},
                    onDecrement = { if(days > 0) {days-- } }
                )
            }
        }
    }
}

fun LendigsGetPendingsElementQuantity(element: Element, pendings: List<PendingElement>): Int {
    if (pendings.any { it.element.name == element.name }) {
        return pendings.first { it.element.name == element.name }.quantity
    } else {
        return 0
    }
}

@Composable
fun PendingAcceptButton(modifier: Modifier) {
    Button(
        onClick = {  },
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
