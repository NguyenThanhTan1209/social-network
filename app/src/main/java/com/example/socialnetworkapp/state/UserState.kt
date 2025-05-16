package com.example.socialnetworkapp.state

import com.example.socialnetworkapp.model.User

sealed class UserState {
    data object Init: UserState()
    data object Loading : UserState()
    data class Success(val user: User, val message: String = "") : UserState()  // Represents a successful data fetch
    data class Error(val message: String) : UserState()  // Represents an error state with an error message
}