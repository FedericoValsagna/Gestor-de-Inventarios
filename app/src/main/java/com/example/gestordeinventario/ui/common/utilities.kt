package com.example.gestordeinventario.ui.common

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

@Composable
fun LogoutButton(modifier: Modifier, logout: () -> Unit){
    TextButton(onClick = { logout()}) {
        Text("Cerrar Sesión", fontSize = 16.sp, modifier = modifier)
    }
}