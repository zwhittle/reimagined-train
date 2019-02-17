package com.awesome.zach.jotunheimrsandbox.utils

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters
import java.time.temporal.WeekFields
import java.util.*

class Utils {
    companion object {
        private lateinit var firstDayOfWeek : DayOfWeek
        private lateinit var lastDayOfWeek: DayOfWeek

        fun firstDayOfThisWeek(locale: Locale = Locale.US) : LocalDate {
            firstDayOfWeek = WeekFields.of(locale).firstDayOfWeek
            return LocalDate.now().with(TemporalAdjusters.previousOrSame(firstDayOfWeek))
        }

        fun lastDayOfThisWeek(locale: Locale = Locale.US) : LocalDate {
            lastDayOfWeek = DayOfWeek.of(((firstDayOfWeek.value + 5) % DayOfWeek.values().size) + 1)
            return LocalDate.now().with(TemporalAdjusters.nextOrSame(lastDayOfWeek))
        }

    }
}