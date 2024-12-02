package com.example.gestordeinventario.ui.common

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.DateFormat.MEDIUM
import java.text.DateFormat.getDateInstance
import java.util.Date

data class CheckboxInfo(
    val isChecked: Boolean,
    val itemIndex: Int
)

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

@Composable
fun RowScope.CheckboxCell(checkedState: Boolean, modifier: Modifier , weight: Float, onCheckedChange: (Boolean) -> Unit) {
    Row(modifier = modifier.border(1.dp, Color.White).weight(weight).size(62.dp),
        horizontalArrangement = Arrangement.Center) {
        Checkbox(onCheckedChange = onCheckedChange, checked = checkedState, modifier = modifier)
    }
}

@Composable
fun EmailField(email : String, onTextFieldChanged: (String) -> Unit) {
    TextField(
        value = email,
        onValueChange = { onTextFieldChanged(it) },
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = "Email") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        singleLine = true,
        maxLines = 1,
        colors = TextFieldDefaults.colors(
            focusedTextColor = Color(0xFF484747),
            focusedContainerColor = Color(0xFFDCDCDC),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}

@Composable
fun PasswordField(password: String, onTextFieldChanged: (String) -> Unit) {
    var passwordVisible by remember { mutableStateOf(false) }

    TextField(
        value = password,
        onValueChange = {onTextFieldChanged(it)},
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = "Password") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        singleLine = true,
        maxLines = 1,
        colors = TextFieldDefaults.colors(
            focusedTextColor = Color(0xFF484747),
            focusedContainerColor = Color(0xFFDCDCDC),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            val image = if (passwordVisible) {
                Icons.Default.Visibility
            } else {
                Icons.Default.VisibilityOff
            }
            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(imageVector = image, contentDescription = if (passwordVisible) "Hide password" else "Show password")
            }
        }
    )
}

@Composable
fun RepeatPasswordField(): String {
    var password by remember { mutableStateOf("")}
    var passwordVisible by remember {mutableStateOf(false)}

    TextField(
        value = password,
        onValueChange = {password = it},
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = "Repetir contraseña") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        singleLine = true,
        maxLines = 1,
        colors = TextFieldDefaults.colors(
            focusedTextColor = Color(0xFF484747),
            focusedContainerColor = Color(0xFFDCDCDC),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            val image = if (passwordVisible) {
                Icons.Default.Visibility
            } else {
                Icons.Default.VisibilityOff
            }
            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(imageVector = image, contentDescription = if (passwordVisible) "Hide password" else "Show password")
            }
        }
    )
    return password
}

fun getFormatDate(date: Date) : String {
    val formatter = getDateInstance(MEDIUM)
    return formatter.format(date).toString()
}