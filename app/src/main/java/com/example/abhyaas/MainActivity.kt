package com.example.abhyaas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.abhyaas.ui.navigation.AppNavHost
import com.example.abhyaas.ui.theme.AbhyaasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AbhyaasTheme {
                val navController = rememberNavController()
                AppNavHost(navController = navController)
            }
        }
    }
}