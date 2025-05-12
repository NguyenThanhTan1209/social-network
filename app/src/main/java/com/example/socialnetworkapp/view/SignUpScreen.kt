package com.example.socialnetworkapp.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.socialnetworkapp.R
import com.example.socialnetworkapp.viewmodel.AuthViewModel
import com.example.socialnetworkapp.viewmodel.UIState
import kotlinx.coroutines.launch

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val userName by viewModel.userName.collectAsState()
    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val userNameError by viewModel.userNameError.collectAsState()
    val emailError by viewModel.emailError.collectAsState()
    val passwordError by viewModel.passwordError.collectAsState()
    val uiState by viewModel.uiState.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(uiState) {
        if (uiState is UIState.Success) {
            navController.navigate(Routes.HOME_SCREEN) {
                popUpTo(Routes.SIGN_UP_SCREEN) { inclusive = true }
            }
        }
        if (uiState is UIState.Error) {
            scope.launch {
                snackbarHostState.showSnackbar(
                    message = "${(uiState as UIState.Error).message}",
                    actionLabel = "Đóng",
                    duration = SnackbarDuration.Short
                )
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { it ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(it)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SignUpTextField(
                onValueChange = { viewModel.updateUsername(it) },
                label = stringResource(R.string.textfield_username),
                placeholder = stringResource(R.string.signup_textfield_placeholder_username),
                supportingText = {
                    if (userNameError)
                        Text(
                            text = "Your username is invalid",
                            color = MaterialTheme.colorScheme.error
                        )
                },
                value = userName,
                isError = userNameError,
                keyboardType = KeyboardType.Text
            )
            Spacer(modifier = Modifier.height(8.dp))
            SignUpTextField(
                onValueChange = { viewModel.updateEmail(it) },
                label = stringResource(R.string.signup_textfield_email),
                placeholder = stringResource(R.string.signup_textfield_placeholder_email),
                supportingText = {
                    if (emailError)
                        Text(
                            text = "Your email is invalid",
                            color = MaterialTheme.colorScheme.error
                        )
                },
                value = email,
                isError = emailError,
                keyboardType = KeyboardType.Email
            )
            Spacer(modifier = Modifier.height(8.dp))
            SignUpTextField(
                onValueChange = { viewModel.updatePassword(it) },
                label = stringResource(R.string.signup_textfield_password),
                placeholder = stringResource(R.string.signup_textfield_placeholder_password),
                supportingText = {
                    if (passwordError)
                        Text(
                            text = "Your password is invalid",
                            color = MaterialTheme.colorScheme.error
                        )
                },
                value = password,
                isError = passwordError,
                keyboardType = KeyboardType.Password
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedButton(
                onClick = { viewModel.signUpWithEmail() },
                modifier = Modifier.fillMaxWidth()
            ) {
                if (uiState is UIState.Loading) {
                    CircularProgressIndicator()
                } else {
                    Text(text = "Sign Up")
                }
            }
            OutlinedButton(onClick = {
                navController.navigate(Routes.SIGN_IN_SCREEN)
            }) {
                Text(text = "Go to Sign In")
            }
        }
    }
}

@Composable
fun SignUpTextField(
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    supportingText: @Composable () -> Unit,
    value: String,
    isError: Boolean,
    keyboardType: KeyboardType
) {
    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label) },
        placeholder = { Text(text = placeholder) },
        supportingText = supportingText,
        isError = isError,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = ImeAction.Next),
    )
}