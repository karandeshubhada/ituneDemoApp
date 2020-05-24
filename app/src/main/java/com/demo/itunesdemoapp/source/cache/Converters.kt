package com.demo.itunesdemoapp.source.cache

import android.net.Uri
import androidx.room.TypeConverter
import java.util.*

class DateConverter {

    @TypeConverter
    fun toDate(timestamp: Long?) = timestamp?.let { Date(it) }

    @TypeConverter
    fun toTimestamp(date: Date?) = date?.time

}

class UriConverter {

    @TypeConverter
    fun toUri(uri: String?) = uri?.let { Uri.parse(it) }

    @TypeConverter
    fun toTimestamp(uri: Uri?) = uri?.toString()
}
