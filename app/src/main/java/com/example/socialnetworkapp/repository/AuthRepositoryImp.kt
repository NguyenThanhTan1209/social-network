package com.example.socialnetworkapp.repository

import com.example.socialnetworkapp.model.User
import com.example.socialnetworkapp.service.AuthenticationService
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class AuthRepositoryImp @Inject constructor(
    private val authenService: AuthenticationService
) : AuthenticationRepository {

    override suspend fun signUpWithEmail(
        email: String,
        password: String,
        userName: String
    ): Result<User> {
        return authenService.signUpWithEmail(
            email = email,
            password = password,
            userName = userName
        )
    }

    override suspend fun signInWithEmail(email: String, password: String): Result<User> {
        return authenService.signInWithEmail(
            email = email,
            password = password,
        )
    }

    override suspend fun getCurrentUser(): FirebaseUser {
        return authenService.getCurrentUser()
    }

    override suspend fun signOut(): String {
        TODO("Not yet implemented")
    }

}