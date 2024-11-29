package com.example.gestordeinventario.ui.common

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.DateFormat.MEDIUM
import java.text.DateFormat.getDateInstance
import java.util.Date

@Composable
fun LogoutButton(modifier: Modifier, logout: () -> Unit){
    TextButton(onClick = { logout()}) {
        Text("Cerrar Sesión", fontSize = 16.sp, modifier = modifier)
    }
}

@Composable
fun RowScope.TableCell(
    text: String,
    weight: Float,
    modifier: Modifier
) {
    Row(modifier = modifier.border(1.dp, Color.White).weight(weight).size(62.dp),
        horizontalArrangement = Arrangement.Center) {
        Text(
            text = text,
            modifier = modifier
                .border(1.dp, Color.White)
                .weight(weight)
                .padding(8.dp)
                .size(52.dp)
                .align(Alignment.CenterVertically)
        )
    }
}

@Composable
fun RowScope.TableClickableCell(
    text: String,
    weight: Float,
    modifier: Modifier,
    navigateToScreen : () -> Unit
) {
    Row(modifier = modifier.border(1.dp, Color.White).weight(weight).size(62.dp),
        horizontalArrangement = Arrangement.Center) {
        Text(
            text = text,
            modifier = modifier
                .clickable { navigateToScreen() }
                .border(1.dp, Color.White)
                .weight(weight)
                .padding(8.dp)
                .size(52.dp)
                .align(Alignment.CenterVertically),
            color = Color(0xFF4EA8E9)
        )
    }
}

@Composable
fun RowScope.TableQuantityCell(
    weight: Float,
    modifier: Modifier,
    quantity: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit
){
    Row(modifier = modifier.border(1.dp, Color.White).weight(weight).size(62.dp),
        horizontalArrangement = Arrangement.Center){
        Text(
            text = "-",
            modifier = modifier
                .clickable {onDecrement()}
                .align(Alignment.CenterVertically)
                .padding(4.dp),
            color = Color(0xFF4EA8E9)
        )
        Text(
            text = quantity.toString(),
            modifier = modifier
                .align(Alignment.CenterVertically)
                .padding(4.dp)
        )
        Text(
            text = "+",
            modifier = modifier
                .clickable {onIncrement()}
                .align(Alignment.CenterVertically)
                .padding(4.dp),
            color = Color(0xFF4EA8E9)
        )
    }
}

fun getFormatDate(date: Date) : String {
    val formatter = getDateInstance(MEDIUM)
    return formatter.format(date).toString()
}