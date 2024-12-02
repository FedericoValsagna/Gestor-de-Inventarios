package com.example.gestordeinventario.ui.replenish

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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gestordeinventario.core.navigation.Pendings
import com.example.gestordeinventario.core.navigation.ScreensNavigation
import com.example.gestordeinventario.model.Element
import com.example.gestordeinventario.model.PendingElement
import com.example.gestordeinventario.model.Provider
import com.example.gestordeinventario.model.Student
import com.example.gestordeinventario.ui.common.LogoutButton
import com.example.gestordeinventario.ui.common.TableCell
import com.example.gestordeinventario.ui.common.TableQuantityCell
import com.example.gestordeinventario.ui.lendings.LendingsViewModel
import com.example.gestordeinventario.ui.lendings.PendingAcceptButton
import com.example.gestordeinventario.ui.pendings.PendingsViewModel
import java.util.ArrayList


@Composable
fun ReplenishScreen(viewModel: ReplenishViewModel, screensNavigation: ScreensNavigation){
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp
    val provider: Provider by viewModel.provider.observeAsState(initial= Provider("", ArrayList()))
    val elements: ArrayList<Element> by viewModel.elements.observeAsState(initial = ArrayList())

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp) ) {
        ReplenishHeader(Modifier.align(Alignment.CenterHorizontally), provider.name)
        Spacer(modifier = Modifier.padding(2.dp))
        HorizontalDivider()
        Box(Modifier.height(screenHeight*0.8f)){
            // Elements(provider.elements, modifier = Modifier)
            Replenisher(viewModel, elements, modifier = Modifier)
        }
        Spacer(modifier = Modifier.padding(2.dp))
        HorizontalDivider()
        Spacer(modifier = Modifier.padding(8.dp))
        ReplenishAcceptButton(viewModel = viewModel, modifier = Modifier.align(Alignment.CenterHorizontally))
        Spacer(modifier = Modifier.padding(8.dp))
        LogoutButton(modifier = Modifier.align(Alignment.Start)){screensNavigation.restart()}
    }
}

@Composable
fun ReplenishHeader(modifier: Modifier, providerName: String) {
    Text(
        text = "Elementos $providerName",
        fontSize = 24.sp,
        modifier = modifier.padding(vertical = 16.dp)
    )
}

@Composable
fun Elements(elements: List<Element>, modifier: Modifier) {
    LazyColumn (
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            Row(Modifier.background(Color.Gray)) {
                TableCell(text = "Elemento", weight = 1f, modifier = modifier)
                TableCell(text = "Cantidad", weight = 1f, modifier = modifier)
            }
        }
        items(elements) {element ->
            Row (modifier = modifier.fillMaxWidth()) {
                TableCell(text = element.name, weight = 1f, modifier = modifier)
                TableCell(text = element.totalQuantity.toString(), weight = 1f, modifier = modifier)
            }
        }
    }
}


@Composable
fun Replenisher(viewModel: ReplenishViewModel, elements: ArrayList<Element>, modifier: Modifier) {

    LazyColumn (
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            Row(Modifier.background(Color.Gray)) {
                TableCell(text = "Elemento", weight = 1f, modifier = modifier)
            }
        }
        items(elements) { element ->
            Row(modifier = modifier.fillMaxWidth()) {
                TableCell(
                    text = element.name,
                    weight = 1f,
                    modifier = modifier)
                TableQuantityCell(
                    weight = 1f,
                    modifier = modifier,
                    quantity = element.totalQuantity,
                    onIncrement = {viewModel.pendingElementQuantityInc(element)},
                    onDecrement = {viewModel.pendingElementQuantityDec(element)}
                )
            }
        }
    }
}

@Composable
fun ReplenishAcceptButton(viewModel: ReplenishViewModel, modifier: Modifier) {
    Button(
        onClick = { viewModel.submitReplenish() },
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

