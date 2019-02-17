package com.awesome.zach.jotunheimrsandbox.db.converters

import android.arch.persistence.room.TypeConverter
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