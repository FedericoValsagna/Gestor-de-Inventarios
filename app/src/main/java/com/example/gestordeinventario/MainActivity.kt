package com.example.gestordeinventario

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gestordeinventario.ui.login.ui.LoginScreen
import com.example.gestordeinventario.ui.login.ui.LoginViewModel
import com.example.gestordeinventario.ui.theme.GestorDeInventarioTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GestorDeInventarioTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    LoginScreen(LoginViewModel())
                }
            }
        }
    }
}

//Usado para hacer algunas pruebas (pantalla de inicio de encargados)
@Composable
fun InicioEncargados() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Inicio Encargados",
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // First Row: Elementos and Alumnos
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            MainButton(text = "Elementos")
            MainButton(text = "Alumnos")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Second Row: Proveedores and Vencimientos
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            MainButton(text = "Proveedores")
            MainButton(text = "Vencimientos")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Logout Button
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

@Preview(showBackground = true)
@Composable
fun InicioEncargadosPreview() {
    GestorDeInventarioTheme {
        InicioEncargados()
    }
}