package com.example.socialnetworkapp.viewmodel

import com.example.socialnetworkapp.model.User

sealed class UIState {
    data object Init: UIState()
    data object Loading : UIState()  // Represents the loading state
    data class Success(val user: User, val message: String = "") : UIState()  // Represents a successful data fetch
    data class Error(val message: String) : UIState()  // Represents an error state with an error message
}