package com.example.gestordeinventario.ui.common

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun LogoutButton(modifier: Modifier, logout: () -> Unit){
    TextButton(onClick = { logout()}) {
        Text("Cerrar Sesión", style = MaterialTheme.typography.body2)
    }
}