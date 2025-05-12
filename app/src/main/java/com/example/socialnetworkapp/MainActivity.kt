package com.example.socialnetworkapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.socialnetworkapp.ui.theme.SocialNetworkAppTheme
import com.example.socialnetworkapp.view.HomeScreen
import com.example.socialnetworkapp.view.Routes
import com.example.socialnetworkapp.view.SignInScreen
import com.example.socialnetworkapp.view.SignUpScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SocialNetworkAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainApp()
                }
            }
        }
    }
}

@Composable
fun MainApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.SIGN_UP_SCREEN) {
        composable(Routes.SIGN_UP_SCREEN) {
            SignUpScreen(navController = navController)
        }
        composable(Routes.SIGN_IN_SCREEN) {
            SignInScreen()
        }
        composable(Routes.HOME_SCREEN) {
            HomeScreen()
        }
    }
}