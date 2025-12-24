package com.plum.superheroapp.data.util

import androidx.room.TypeConverter
import kotlin.collections.joinToString
import kotlin.text.split

class Converters {
    @TypeConverter
    fun fromListToString(list: List<String>): String {
        return list.joinToString(separator = ",")
    }

    @TypeConverter
    fun toList(string: String): List<String> {
        return string.split(",")
    }

}