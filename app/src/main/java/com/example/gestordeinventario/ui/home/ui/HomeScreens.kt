package com.example.gestordeinventario.ui.home.ui

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
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gestordeinventario.ui.common.LogoutButton

@Composable
fun HomeScreen(accessPrivileges: String, logout: () -> Unit){
    Box(
        Modifier
            .fillMaxSize()
            .padding(16.dp)) {
        if(accessPrivileges == "admin"){
            HomeAdmins(modifier = Modifier.align(Alignment.Center), logout = logout)
        }
        else {
            HomeStudents(modifier = Modifier.align(Alignment.Center), logout = logout)
        }
    }
}

@Composable
fun HomeAdmins(modifier: Modifier, logout: () -> Unit) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HomeHeader(modifier = modifier)
        Spacer(modifier = modifier.height(16.dp))
        HomeAdminsButtons(modifier = modifier)
        Spacer(modifier = modifier.height(16.dp))
        LogoutButton(modifier = modifier, logout = logout)
    }
}

@Composable
fun HomeStudents(modifier: Modifier, logout: () -> Unit) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HomeHeader(modifier = modifier)
        Spacer(modifier = modifier.height(16.dp))
        HomeStudentsButtons(modifier = modifier)
        LogoutButton(modifier = modifier, logout = logout)
    }
}

@Composable
fun HomeAdminsButtons(modifier: Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        HomeButton(text = "Elementos")
        HomeButton(text = "Alumnos")
    }

    Spacer(modifier = modifier.height(16.dp))
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        HomeButton(text = "Proveedores")
        HomeButton(text = "Vencimientos")
    }
}

@Composable
fun HomeStudentsButtons(modifier: Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        HomeButton(text = "Elementos")
        HomeButton(text = "Pendientes")
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
fun HomeButton(text: String) {
    Button(
        onClick = { /* Accion al presionar el boton */ },
        modifier = Modifier
            .width(150.dp)
            .height(100.dp)
    ) {
        Text(text = text, textAlign = TextAlign.Center)
    }
}

//@Preview(showBackground = true, showSystemUi = true)