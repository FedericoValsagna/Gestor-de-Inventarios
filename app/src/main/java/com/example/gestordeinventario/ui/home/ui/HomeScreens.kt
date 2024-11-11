package com.example.gestordeinventario.ui.home.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeAdmins() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "INICIO ENCARGADOS",
            fontSize = 28.sp,
            modifier = Modifier.padding(vertical = 16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            MainButton(text = "Elementos")
            MainButton(text = "Alumnos")
        }

        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            MainButton(text = "Proveedores")
            MainButton(text = "Vencimientos")
        }
        Spacer(modifier = Modifier.height(16.dp))
        TextButton(onClick = { /* Accion de cerrar sesion */ }) {
            Text("Cerrar Sesión", style = MaterialTheme.typography.body2)
        }
    }
}

@Composable
fun MainButton(text: String) {
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