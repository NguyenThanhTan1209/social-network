package com.example.socialnetworkapp.service

import com.example.socialnetworkapp.model.User

interface AuthenticationService {
    suspend fun signUpWithEmail(email: String, password: String, userName: String): Result<User>
    suspend fun signInWithEmail(email: String, password: String): Result<User?>
    suspend fun signInWithGoogle(): Result<User>
    fun isUserLoggedIn(): Boolean
    suspend fun getCurrentUser(): Result<User>
    suspend fun signOut(): String
}