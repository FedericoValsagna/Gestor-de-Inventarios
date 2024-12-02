package com.example.gestordeinventario.ui.student_elements

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
import com.example.gestordeinventario.model.Element
import com.example.gestordeinventario.ui.common.LogoutButton
import com.example.gestordeinventario.ui.common.TableCell
import com.example.gestordeinventario.ui.elements.ElementsViewModel


@Composable
fun StudentElementsScreen(viewModel: StudentElementsViewModel, screensNavigation: ScreensNavigation) {
    val elementsList: List<Element> by viewModel.elementsList.observeAsState(initial = emptyList())
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        StudentElementsListHeader(Modifier.align(Alignment.CenterHorizontally))
        Spacer(modifier = Modifier.padding(2.dp))
        HorizontalDivider()
        Box(Modifier.height(screenHeight * 0.8f)) {
            StudentElementsList(
                elements = elementsList,
                modifier = Modifier,
            )
        }
        Spacer(modifier = Modifier.padding(2.dp))
        HorizontalDivider()
        LogoutButton(modifier = Modifier.align(Alignment.Start)) { screensNavigation.restart() }
    }
}

@Composable
fun StudentElementsListHeader(modifier: Modifier) {
    Text(
        text = "Elementos",
        fontSize = 24.sp,
        modifier = modifier.padding(vertical = 16.dp)
    )
}

@Composable
fun StudentElementsList(
    elements: List<Element>,
    modifier: Modifier,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            Row(Modifier.background(Color.Gray)) {
                TableCell(text = "Nombre", weight = 1f, modifier = modifier)
                TableCell(text = "Stock", weight = 1f, modifier = modifier)
            }
        }
        items(elements) { element ->
            Row(modifier = modifier.fillMaxWidth()) {
                TableCell(
                    text = element.name,
                    weight = 1f,
                    modifier = modifier)
                TableCell(
                    text = element.totalQuantity.toString(),
                    weight = 1f,
                    modifier = modifier
                )
            }
        }
    }
}