package com.example.socialnetworkapp.service

import com.example.socialnetworkapp.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthenticationServiceImp @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore
) : AuthenticationService {

    private val userCollectionPath = "users"
    private val userNameField = "userName"

    override suspend fun signUpWithEmail(
        email: String,
        password: String,
        userName: String
    ): Result<User> {
        try {
            // Check username is exist
            val usersCollection = firebaseFirestore.collection(userCollectionPath)
            val querySnapshot = usersCollection
                .whereEqualTo(userNameField, userName)
                .limit(1)
                .get()
                .await()
            if (!querySnapshot.isEmpty){
                return Result.failure(Exception("Username already exists. Please choose another."))
            }

            // Create user with Firebase Authentication
            val authResult = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            val userId = authResult.user?.uid ?: return Result.failure(Exception("User ID is null"))

            // Create user document in Firestore
            val user = User(
                userID = userId,
                email = email,
                userName = userName
            )

            // Save user to Firestore
            firebaseFirestore.collection(userCollectionPath).document(userId).set(user).await()

            return Result.success(user)
        } catch (e: Exception){
            return Result.failure(e)
        }
    }

    override suspend fun signInWithEmail(email: String, password: String): Result<User> {
        TODO("Not yet implemented")
    }

    override suspend fun getCurrentUser(): FirebaseUser {
        TODO("Not yet implemented")
    }

    override suspend fun signOut(): String {
        TODO("Not yet implemented")
    }
}