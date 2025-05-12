package com.example.socialnetworkapp.repository

import com.example.socialnetworkapp.model.User
import com.google.firebase.auth.FirebaseUser

interface AuthenticationRepository {
    suspend fun signUpWithEmail(email: String, password: String, userName: String): Result<User>
    suspend fun signInWithEmail(email: String, password: String): Result<User>
    suspend fun getCurrentUser(): FirebaseUser
    suspend fun signOut(): String
}