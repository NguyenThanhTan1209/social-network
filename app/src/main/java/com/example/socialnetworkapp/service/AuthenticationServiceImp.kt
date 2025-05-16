package com.example.socialnetworkapp.service

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import com.example.socialnetworkapp.R
import com.example.socialnetworkapp.model.User
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.Companion.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthenticationServiceImp @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore,
    private val context: Context,
) : AuthenticationService {

    private val userCollectionPath = "users"
    private val userNameField = "userName"
    private lateinit var credentialManager: CredentialManager

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
            if (!querySnapshot.isEmpty) {
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
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    override suspend fun signInWithEmail(email: String, password: String): Result<User?> {
        try {
            // Create user with Firebase Authentication
            val authResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            val userId = authResult.user?.uid
                ?: return Result.failure(Exception("Username or password is not correct. Try again."))

            var user: User? = null
            // Save user to Firestore
            firebaseFirestore.collection(userCollectionPath).document(userId)
                .get().addOnSuccessListener {
                    user = it.toObject<User>()
                }.addOnFailureListener {
                    throw Exception("lay user that bai")
                }.await()
            return Result.success(user!!)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    private suspend fun buildCredentialManager(): GetCredentialResponse {
        try {
            credentialManager = CredentialManager.create(context)

            val googleIdOption = GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(false)
                .setServerClientId(context.getString(R.string.default_web_client_id))
                .setAutoSelectEnabled(false)
                .build()

            val request = GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build()

            val response = credentialManager.getCredential(
                context = context,
                request = request
            )
            return response
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun signInWithGoogle(): Result<User> {
        try {
            val result = buildCredentialManager()
            val credential = result.credential

            if (credential is CustomCredential && credential.type == TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                // Create Google ID Token
                val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)

                // Sign in to Firebase with using the token
                val credential =
                    GoogleAuthProvider.getCredential(googleIdTokenCredential.idToken, null)
                val authResult = firebaseAuth.signInWithCredential(credential).await()

                val userId =
                    authResult.user?.uid ?: return Result.failure(Exception("User ID is null"))

                // Create user document in Firestore
                val user = User(
                    userID = userId,
                    email = authResult.user!!.email!!,
                    userName = authResult.user!!.displayName!!
                )

                // Save user to Firestore
                firebaseFirestore.collection(userCollectionPath).document(userId).set(user).await()

                return Result.success(user)
            } else {
                return Result.failure(throw Exception("Credential is not of type Google ID!"))
            }
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    override fun isUserLoggedIn(): Boolean {
        val result = (firebaseAuth.currentUser != null)
        return result
    }

    override suspend fun getCurrentUser(): User {
        TODO("Not yet implemented")
    }

    override suspend fun signOut(): String {
        TODO("Not yet implemented")
    }
}