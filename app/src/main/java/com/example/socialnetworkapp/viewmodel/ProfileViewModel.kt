package com.example.socialnetworkapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.socialnetworkapp.repository.AuthenticationRepository
import com.example.socialnetworkapp.state.AuthState
import com.example.socialnetworkapp.state.UserState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val repository: AuthenticationRepository) :
    ViewModel() {
    private val _userInfo = MutableStateFlow<UserState>(UserState.Init)
    val userInfo: StateFlow<UserState> = _userInfo

    fun getUserInfo() {
        viewModelScope.launch {
            _userInfo.value = UserState.Loading
            try {
                val result = repository.getCurrentUser()
                result.fold(
                    onSuccess = { user ->
                        _userInfo.value =
                            UserState.Success(
                                user = user,
                                message = "Get user info success"
                            )
                    },
                    onFailure = { throwable ->
                        _userInfo.value =
                            UserState.Error(message = throwable.message ?: "Has error when get user info")
                    })
            } catch (e: Exception) {
                _userInfo.value = UserState.Error(message = e.message.toString())
            }
        }
    }
}