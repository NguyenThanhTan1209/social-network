package com.example.socialnetworkapp.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.socialnetworkapp.R
import com.example.socialnetworkapp.viewmodel.SignInViewModel
import com.example.socialnetworkapp.viewmodel.UIState
import kotlinx.coroutines.launch

@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: SignInViewModel = hiltViewModel()
) {
    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val emailError by viewModel.emailError.collectAsState()
    val passwordError by viewModel.passwordError.collectAsState()
    val uiState by viewModel.uiState.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(uiState) {
        if (uiState is UIState.Success) {
            navController.navigate(Routes.HOME_SCREEN) {
                popUpTo(Routes.SIGN_IN_SCREEN) { inclusive = true }
            }
        }
        if (uiState is UIState.Error) {
            scope.launch {
                snackbarHostState.showSnackbar(
                    message = (uiState as UIState.Error).message,
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
                keyboardType = KeyboardType.Text
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
                onClick = {
                    viewModel.signInWithEmail()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                if (uiState is UIState.Loading) {
                    CircularProgressIndicator()
                } else {
                    Text(text = "Sign In")
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedButton(
                onClick = {
                    viewModel.signInWithGoogle()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                if (uiState is UIState.LoadingWithGoogle) {
                    CircularProgressIndicator()
                } else {
                    Text(text = "Sign In With Google")
                }
            }
            OutlinedButton(onClick = {
                navController.navigate(Routes.SIGN_UP_SCREEN)
            }) {
                Text(text = "Go to sign up")
            }
        }
    }
}