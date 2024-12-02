package com.example.gestordeinventario.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gestordeinventario.R
import com.example.gestordeinventario.core.navigation.ScreensNavigation
import com.example.gestordeinventario.ui.common.EmailField
import com.example.gestordeinventario.ui.common.PasswordField
import kotlinx.coroutines.launch


@Composable
fun LoginScreen(viewModel: LoginViewModel, screensNavigation: ScreensNavigation) {
    Box(
        Modifier
            .fillMaxSize()
            .padding(16.dp)) {
        Login(Modifier.align(Alignment.Center), viewModel, screensNavigation)
    }
}

@Composable
fun Login(modifier: Modifier, viewModel: LoginViewModel, screensNavigation: ScreensNavigation) {

    val email: String by viewModel.email.observeAsState(initial = "")
    val password: String by viewModel.password.observeAsState(initial = "")
    val loginEnable: Boolean by viewModel.loginEnable.observeAsState(initial = false)
    val isLoading: Boolean by viewModel.isLoading.observeAsState(initial = false)
    val couroutineScope = rememberCoroutineScope()
    val showErrorDialog: Boolean by viewModel.showErrorDialog.observeAsState(initial = false)

    if(isLoading){
        Box(Modifier.fillMaxSize()){
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        }
    }
    else{
        Column(modifier = modifier) {
            LoginHeaderText(Modifier.align(Alignment.CenterHorizontally))
            Spacer(modifier = Modifier.padding(16.dp))
            LoginHeaderImage(Modifier.align(Alignment.CenterHorizontally))
            Spacer(modifier = Modifier.padding(16.dp))
            EmailField(email) { viewModel.onLoginChanged(it, password) }
            Spacer(modifier = Modifier.padding(4.dp))
            PasswordField(password) { viewModel.onLoginChanged(email, it) }
            Spacer(modifier = Modifier.padding(8.dp))
            SignIn(Modifier.align(Alignment.Start)) { screensNavigation.navigateToRegistration()}
            Spacer(modifier = Modifier.padding(16.dp))
            LoginButton(Modifier.align(Alignment.CenterHorizontally), loginEnable) {
                    couroutineScope.launch { viewModel.onLoginSelected(screensNavigation) }
            }
        }
        if (showErrorDialog){
            LoginErrorDialog("Email y/o contraseña inválidos"){viewModel.dismissErrorDialog()}
        }
    }
}

@Composable
fun LoginButton(modifier: Modifier, loginEnable: Boolean, onLoginSelected: () -> Unit) {
    Button(
            onClick = { onLoginSelected() },
            modifier = modifier
                    .fillMaxWidth()
                    .height(48.dp),
            colors = ButtonDefaults.buttonColors(
                                                containerColor = Color(0xFF2F3DA2),
                                                disabledContainerColor = Color(0xFF6F76AD),
                                                contentColor = Color.White,
                                                disabledContentColor = Color.White
                                    ),
            enabled = loginEnable
    ) {
            Text(text = "Iniciar Sesión")
    }
}

@Composable
fun LoginHeaderText(modifier: Modifier) {
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
}

@Composable
fun SignIn(modifier: Modifier, navigateToRegistration: () -> Unit) {
    Text(
        text = "Registrarse",
        modifier = modifier.clickable { navigateToRegistration() },
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFF4EA8E9)
    )
}

@Composable
fun LoginHeaderImage(modifier: Modifier) {
    Image(
        painter = painterResource(id = R.drawable.esc_fiuba),
        contentDescription = "HeaderEscudoFiuba",
        modifier = modifier.size(200.dp)
        )
}

@Composable
fun LoginErrorDialog(message: String, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = {
            Text(text = "Error")
        },
        text = {
            Text(text = message)
        },
        confirmButton = {
            Button(onClick = { onDismiss() }) {
                Text("OK")
            }
        }
    )
}