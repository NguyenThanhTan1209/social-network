package com.example.socialnetworkapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.socialnetworkapp.repository.AuthenticationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(private val repository: AuthenticationRepository) :
    ViewModel() {
    // User input fields and validation errors
    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email
    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password
    private val _emailError = MutableStateFlow(false)
    val emailError: StateFlow<Boolean> = _emailError
    private val _passwordError = MutableStateFlow(false)
    val passwordError: StateFlow<Boolean> = _passwordError

    // Update email input and validate
    fun updateEmail(input: String) {
        _email.value = input
        _emailError.value = isEmailInvalid(input)
    }

    // Update password input and validate
    fun updatePassword(input: String) {
        _password.value = input
        _passwordError.value = isPasswordInvalid(password = input)
    }

    private fun isPasswordInvalid(password: String): Boolean {
        if (password.isBlank())
            return true
        if (password.length < 8)
            return true
        if (!password.any { it.isUpperCase() })
            return true
        if (!password.any { it.isLowerCase() })
            return true
        if (!password.any { it.isDigit() })
            return true
        if (password.contains(" "))
            return true
        return false
    }

    private fun isEmailInvalid(email: String): Boolean {
        if (email.isBlank())
            return true
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
            return true
        if (email.length > 255)
            return true
        return false
    }

    // UI state
    private val _uiState = MutableStateFlow<UIState>(UIState.Init)
    val uiState: StateFlow<UIState> = _uiState

    fun signInWithEmail() {
        if (_email.value.isEmpty() || _emailError.value) {
            _uiState.value = UIState.Error(message = "Your email is incorrect.")
            return
        }
        if (_password.value.isEmpty() || _passwordError.value) {
            _uiState.value = UIState.Error(message = "Your password is incorrect.")
            return
        }
        viewModelScope.launch {
            _uiState.value = UIState.Loading
            try {
                val result = repository.signInWithEmail(
                    email = email.value,
                    password = password.value,
                )
                result.fold(
                    onSuccess = { user ->
                        _uiState.value =
                            UIState.Success(
                                user = user!!,
                                message = "Sign up successfully!"
                            )
                    },
                    onFailure = { throwable ->
                        _uiState.value =
                            UIState.Error(message = "Username or password is not correct")
                    }
                )
            } catch (e: Exception) {
                _uiState.value = UIState.Error(message = "Sign up failed!: $e")
            }
        }
    }
}