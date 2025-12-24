package com.plum.superheroapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.plum.superheroapp.presentation.navigation.NavigationGraph
import com.plum.superheroapp.presentation.ui.theme.SuperheroappTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()

            SuperheroappTheme {
                NavigationGraph(navController)
            }
        }
    }
}