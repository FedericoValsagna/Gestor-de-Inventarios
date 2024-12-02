package com.example.gestordeinventario.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gestordeinventario.core.navigation.ScreensNavigation
import com.example.gestordeinventario.ui.common.LogoutButton

@Composable
fun HomeScreen(padron: String, isProfessor: Boolean, screensNavigation: ScreensNavigation){
    Box(
        Modifier
            .fillMaxSize()
            .padding(16.dp)) {
        if(isProfessor){
            HomeAdmins(modifier = Modifier.align(Alignment.Center), screensNavigation = screensNavigation)
        }
        else {
            HomeStudents(padron, modifier = Modifier.align(Alignment.Center), screensNavigation = screensNavigation)
        }
    }
}

@Composable
fun HomeAdmins(modifier: Modifier, screensNavigation: ScreensNavigation) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HomeHeader(modifier = modifier)
        Spacer(modifier = modifier.height(16.dp))
        HomeAdminsButtons(modifier = modifier, screensNavigation = screensNavigation)
        Spacer(modifier = modifier.height(16.dp))
        LogoutButton(modifier = modifier){screensNavigation.restart()}
    }
}

@Composable
fun HomeStudents(padron: String, modifier: Modifier, screensNavigation: ScreensNavigation) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HomeHeader(modifier = modifier)
        Spacer(modifier = modifier.height(16.dp))
        HomeStudentsButtons(padron, modifier = modifier, screensNavigation = screensNavigation)
        LogoutButton(modifier = modifier){screensNavigation.restart()}
    }
}

@Composable
fun HomeAdminsButtons(modifier: Modifier, screensNavigation: ScreensNavigation) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        HomeButton(text = "Elementos"){screensNavigation.navigateToElements()}
        HomeButton(text = "Alumnos"){screensNavigation.navigateToStudentsList()}
    }

    Spacer(modifier = modifier.height(16.dp))
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        HomeButton(text = "Proveedores"){screensNavigation.navigateToProvidersList()}
        HomeButton(text = "Vencimientos"){screensNavigation.navigateToDevolutions()}
    }
}

@Composable
fun HomeStudentsButtons(padron: String, modifier: Modifier, screensNavigation: ScreensNavigation) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        HomeButton(text = "Elementos"){screensNavigation.navigateToStudentElements()}
        HomeButton(text = "Pendientes"){screensNavigation.navigateToStudentPendings(padron)}
    }
    Spacer(modifier = modifier.height(16.dp))
}

@Composable
fun HomeHeader(modifier: Modifier) {
    Text(
        text = "INICIO ENCARGADOS",
        fontSize = 28.sp,
        modifier = modifier.padding(vertical = 16.dp)
    )
}

@Composable
fun HomeButton(text: String, navigate: () -> Unit) {
    Button(
        onClick = {navigate()},
        modifier = Modifier
            .width(150.dp)
            .height(100.dp)
    ) {
        Text(text = text, textAlign = TextAlign.Center)
    }
}

//@Preview(showBackground = true, showSystemUi = true)