package com.example.socialnetworkapp.service

import com.example.socialnetworkapp.model.User
import com.google.firebase.auth.FirebaseUser

interface AuthenticationService {
    suspend fun signUpWithEmail(email: String, password: String, userName: String): Result<User>
    suspend fun signInWithEmail(email: String, password: String): Result<User>
    suspend fun getCurrentUser(): FirebaseUser
    suspend fun signOut(): String
}