package com.prabal.animatorlearn

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.prabal.animatorlearn.screens.AnimeHome
import com.prabal.animatorlearn.screens.AnimieDetail
import com.prabal.animatorlearn.ui.theme.AnimatorLearnTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AnimatorLearnTheme {

                    NavigationInApp()

            }
        }
    }
}

@Composable
fun NavigationInApp(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "AnimeHome") {
        composable("AnimeHome") {
            AnimeHome(navController)
        }

        composable("AnimieDetailScreen/{id}/{title}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")
            val title = backStackEntry.arguments?.getString("title")
            AnimieDetail(navController,id.toString(), title.toString())
        }
    }
}
