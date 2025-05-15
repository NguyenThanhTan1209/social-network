package com.example.socialnetworkapp.dependency

import android.app.Application
import android.content.Context
import androidx.credentials.CredentialManager
import com.example.socialnetworkapp.repository.AuthRepositoryImp
import com.example.socialnetworkapp.repository.AuthenticationRepository
import com.example.socialnetworkapp.service.AuthenticationService
import com.example.socialnetworkapp.service.AuthenticationServiceImp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideApplicationContext(application: Application): Context {
        return application.applicationContext
    }
}

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {
    @Binds
    abstract fun bindAuthService(authServiceImp: AuthenticationServiceImp): AuthenticationService
}

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindAuthRepository(authRepositoryImp: AuthRepositoryImp): AuthenticationRepository
}