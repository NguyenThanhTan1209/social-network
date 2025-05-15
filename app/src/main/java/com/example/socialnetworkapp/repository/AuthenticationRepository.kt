package com.example.socialnetworkapp.repository

import com.example.socialnetworkapp.model.User

interface AuthenticationRepository {
    suspend fun signUpWithEmail(email: String, password: String, userName: String): Result<User>
    suspend fun signInWithGoogle(): Result<User>
    suspend fun signInWithEmail(email: String, password: String): Result<User?>
    suspend fun getCurrentUser(): User
    suspend fun signOut(): String
}