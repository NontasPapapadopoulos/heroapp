package com.plum.superheroapp.presentation

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner

class SuperHeroAppTestRunner: AndroidJUnitRunner() {
    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application? {
        return super.newApplication(cl, HiltComponentActivity::class.java.name, context)
    }
}