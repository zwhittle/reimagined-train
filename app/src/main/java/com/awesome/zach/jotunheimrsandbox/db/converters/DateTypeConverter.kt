package com.awesome.zach.jotunheimrsandbox.db.converters

import android.arch.persistence.room.TypeConverter
import com.awesome.zach.jotunheimrsandbox.utils.Constants.Companion.dateFormatter
import java.util.*

class DateTypeConverter {
    @TypeConverter
    fun dateFromString(dateString: String?): Date? {
        if (dateString == null) {
            return null
        }

        return dateFormatter.parse(dateString)
    }

    @TypeConverter
    fun dateToString(date: Date?): String? {
        if (date == null) {
            return null
        }

        return dateFormatter.format(date)
    }
}