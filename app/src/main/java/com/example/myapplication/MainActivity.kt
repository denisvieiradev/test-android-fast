package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                App()
            }
        }
    }
}

@Composable
fun App(modifier: Modifier = Modifier) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        val isLogged = remember { mutableStateOf(false) }

        AnimatedVisibility(visible = isLogged.value) {
            WelcomeScreen {
                isLogged.value = false
            }
        }

        AnimatedVisibility(visible = !isLogged.value) {
            Login(
                modifier = Modifier.padding(innerPadding)
            ) {
                isLogged.value = true
            }
        }
    }
}

@Composable
fun WelcomeScreen(modifier: Modifier = Modifier, onPressLogout: () -> Unit = {}) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(50.dp)
    ) {
        Text(
            text = "Welcome to the app ",

            )
        Button(onClick = { onPressLogout() }) {
            Text(
                text = "LOGOUT",
            )
        }
    }

}

@Preview
@Composable
fun Login(modifier: Modifier = Modifier, onLoginSuccess: () -> Unit = {}) {

    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val shouldShowErrorMessage = remember {
        mutableStateOf(false)
    }

    fun validateLogin() {
        if (email.value == "john@example.com" && password.value == "123456") {
            onLoginSuccess()
        } else {
            shouldShowErrorMessage.value = true
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(50.dp)
    ) {
        TextField(
            value = email.value,
            onValueChange = { email.value = it },
            label = { Text("Email") }
        )

        TextField(
            value = password.value,
            onValueChange = { password.value = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation()
        )
        Button(onClick = { validateLogin() }) {
            Text(
                text = "LOGIN",
            )
        }

        if (shouldShowErrorMessage.value) {
            Text(
                text = "Invalid credentials",
                modifier = modifier
            )
        }
    }
}