package com.plum.superheroapp.presentation

import android.app.Application
import androidx.activity.ComponentActivity
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class HeroApplication: Application() {
}


@AndroidEntryPoint
class HiltComponentActivity: ComponentActivity()