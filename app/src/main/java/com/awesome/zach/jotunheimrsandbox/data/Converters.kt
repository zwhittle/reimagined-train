package com.awesome.zach.jotunheimrsandbox.data

import androidx.room.TypeConverter
import java.time.Instant
import java.time.LocalDate
import java.util.regex.Pattern

class Converters {
    @TypeConverter
    fun dateFromString(dateString: String?): LocalDate? {
        if (dateString == null) {
            return null
        }

        return LocalDate.parse(dateString)
    }

    @TypeConverter
    fun dateToString(date: LocalDate?): String? {
        if (date == null) {
            return null
        }

        return date.toString()
    }

    @TypeConverter
    fun fromTimestamp(value: Long?): Instant? {
        return if (value == null) null else Instant.ofEpochMilli(value)
    }

    @TypeConverter
    fun datetimeToTimestamp(date: Instant?): Long? {
        return date?.toEpochMilli()
    }

    @TypeConverter
    fun fromString(value: String?): Pattern? {
        return if (value == null) null else Pattern.compile(value)
    }

    @TypeConverter
    fun patternToString(pattern: Pattern?): String? {
        return pattern?.pattern()
    }
}