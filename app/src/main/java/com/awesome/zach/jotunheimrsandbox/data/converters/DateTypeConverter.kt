package com.awesome.zach.jotunheimrsandbox.data.converters

import androidx.room.TypeConverter
import java.time.LocalDate

class DateTypeConverter {
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
}