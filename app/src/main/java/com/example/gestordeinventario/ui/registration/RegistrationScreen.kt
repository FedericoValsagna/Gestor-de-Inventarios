package com.example.gestordeinventario.ui.registration

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gestordeinventario.R
import com.example.gestordeinventario.core.navigation.ScreensNavigation
import kotlinx.coroutines.launch


@Composable
fun RegistrationScreen(viewModel: RegistrationViewModel, screensNavigation: ScreensNavigation) {
    Box(
        Modifier
            .fillMaxSize()
            .padding(16.dp)) {
        Registration(Modifier.align(Alignment.Center), viewModel, screensNavigation)
    }
}

@Composable
fun Registration(modifier: Modifier, viewModel: RegistrationViewModel, screensNavigation: ScreensNavigation) {

    val email: String by viewModel.email.observeAsState(initial = "")
    val password: String by viewModel.password.observeAsState(initial = "")
    val name: String by viewModel.name.observeAsState(initial = "")
    val padron: String by viewModel.padron.observeAsState(initial = "")
    val isAdmin: Boolean by viewModel.isAdmin.observeAsState(initial = false)
    var passwordRepeat: String
    val registrationEnable: Boolean by viewModel.registrationEnable.observeAsState(initial = false)
    val isLoading: Boolean by viewModel.isLoading.observeAsState(initial = false)
    val couroutineScope = rememberCoroutineScope()



    if(isLoading){
        Box(Modifier.fillMaxSize()){
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        }
    }
    else{
        Column(modifier = modifier) {
            HeaderText(Modifier.align(Alignment.CenterHorizontally))
            Spacer(modifier = Modifier.padding(16.dp))
            // HeaderImage(Modifier.align(Alignment.CenterHorizontally))
            Spacer(modifier = Modifier.padding(16.dp))
            EmailField(email) { viewModel.onRegistrationChanged(it, password, name, padron, isAdmin) }
            Spacer(modifier = Modifier.padding(4.dp))
            PasswordField(password) { viewModel.onRegistrationChanged(email, it, name, padron, isAdmin) }
            Spacer(modifier = Modifier.padding(4.dp))
            passwordRepeat = repeatPasswordField()
            Spacer(modifier = Modifier.padding(4.dp))
            NameField(name) { viewModel.onRegistrationChanged(email, password, it, padron, isAdmin)}
            Spacer(modifier = Modifier.padding(4.dp))
            PadronField(padron) { viewModel.onRegistrationChanged(email, password, name, it, isAdmin) }
            Spacer(modifier = Modifier.padding(4.dp))
            AdminField(isAdmin) { viewModel.onRegistrationChanged(email, password, name, padron, it)}
            Spacer(modifier = Modifier.padding(16.dp))
            RegistrationButton(Modifier.align(Alignment.CenterHorizontally), registrationEnable) {
                couroutineScope.launch { viewModel.onRegistrationSelected()
                    if(password == passwordRepeat) {
                        screensNavigation.restart()
                    }
                }
            }
        }
    }
}

@Composable
fun RegistrationButton(modifier: Modifier, registrationEnable: Boolean, onRegistrationSelected: () -> Unit) {
    Button(
        onClick = { onRegistrationSelected() },
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF2F3DA2),
            disabledContainerColor = Color(0xFF6F76AD),
            contentColor = Color.White,
            disabledContentColor = Color.White
        ),
        enabled = registrationEnable
    ) {
        Text(text = "Registrarse")
    }
}

@Composable
fun HeaderText(modifier: Modifier) {
    Text(
        text = "Gestor de Inventario",
        fontSize = 32.sp,
        modifier = modifier,
        color = Color(0xFF3986E5)
    )
    Spacer(modifier = Modifier.padding(8.dp))
    Text(
        text = "Laboratorio de Electrónica",
        fontSize = 28.sp,
        modifier = modifier,
        color = Color(0xFF3986E5)
    )
    Spacer(modifier = Modifier.padding(8.dp))
    Text(
        text = "Registrarse",
        fontSize = 28.sp,
        modifier = modifier,
        color = Color(0xFF3986E5)
    )
}

@Composable
fun PasswordField(password: String, onTextFieldChanged: (String) -> Unit) {
    TextField(
        value = password,
        onValueChange = {onTextFieldChanged(it)},
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = "Ingresar contraseña") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
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
fun repeatPasswordField(): String {
    var password by remember { mutableStateOf("")}

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
        )
    )
    return password
}

@Composable
fun EmailField(email : String, onTextFieldChanged: (String) -> Unit) {
    TextField(
        value = email,
        onValueChange = { onTextFieldChanged(it) },
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = "Ingresar correo") },
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
fun NameField(username: String, onTextFieldChanged: (String) -> Unit) {
    TextField(
        value = username,
        onValueChange = { onTextFieldChanged(it) },
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = "Ingresar nombre") },
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
fun PadronField(padron: String, onTextFieldChanged: (String) -> Unit) {
    TextField(
        value = padron,
        onValueChange = { onTextFieldChanged(it) },
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = "Ingresar padron") },
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
fun AdminField(isAdmin: Boolean, onCheckedChanged: (Boolean) -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically){
        Text(
            text = "Profesor: ",
        )
        Checkbox(
            checked = isAdmin,
            onCheckedChange = { onCheckedChanged(it) }
        )
    }
}

@Composable
fun HeaderImage(modifier: Modifier) {
    Image(
        painter = painterResource(id = R.drawable.esc_fiuba),
        contentDescription = "HeaderEscudoFiuba",
        modifier = modifier.size(200.dp)
    )
}
