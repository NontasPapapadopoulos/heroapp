package com.plum.superheroapp.presentation.exception

import android.content.res.Resources

import com.plum.superheroapp.R
import java.net.UnknownHostException

private const val unknownErrorTag: String = "Unknown Error: "

fun Resources.errorStringResource(throwable: Throwable): String {
    return when (throwable) {
        is UnknownHostException -> getString(R.string.internet_off)
        else -> throwable.message ?: (unknownErrorTag + throwable.javaClass.name)
    }
}