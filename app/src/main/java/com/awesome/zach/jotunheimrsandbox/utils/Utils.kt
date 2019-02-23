package com.awesome.zach.jotunheimrsandbox.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar
import java.lang.Long.parseLong
import java.lang.Long.toHexString
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters
import java.time.temporal.WeekFields
import java.util.*

object Utils {
    private lateinit var firstDayOfWeek: DayOfWeek
    private lateinit var lastDayOfWeek: DayOfWeek

    fun firstDayOfThisWeek(locale: Locale = Locale.US): LocalDate {
        firstDayOfWeek = WeekFields.of(locale).firstDayOfWeek
        return LocalDate.now().with(TemporalAdjusters.previousOrSame(firstDayOfWeek))
    }

    fun lastDayOfThisWeek(locale: Locale = Locale.US): LocalDate {
        lastDayOfWeek = DayOfWeek.of(((firstDayOfWeek.value + 5) % DayOfWeek.values().size) + 1)
        return LocalDate.now().with(TemporalAdjusters.nextOrSame(lastDayOfWeek))
    }

    fun inverseHex(hexParam: String?): String {
        if (hexParam == null) {
            return ""
        }

        var alphaPassed = false
        val c = 255

        var hex = hexParam
        var hAlpha = ""
        val hRed: Long
        val hGreen: Long
        val hBlue: Long

        if (hex.startsWith("#")) {
            hex = hex.substring(1)
        }

        if (hex.length == 8) {
            hAlpha = hex.substring(0, 2)
            hex = hex.substring(2)
            alphaPassed = true
        }

        hRed = parseLong(hex.substring(0, 2), 16)
        hGreen = parseLong(hex.substring(2, 4), 16)
        hBlue = parseLong(hex.substring(4), 16)

        val iRed = c - hRed
        val iGreen = c - hGreen
        val iBlue = c - hBlue

        val nhRed = toHexString(iRed).padStart(2, '0')
        val nhGreen = toHexString(iGreen).padStart(2, '0')
        val nhBlue = toHexString(iBlue).padStart(2, '0')

        return if (alphaPassed) {
            "#$hAlpha$nhRed$nhGreen$nhBlue"
        } else {
            "#ff$nhRed$nhGreen$nhBlue"
        }
    }

    fun showSnackbar(root: View, message: String) {
        Snackbar.make(root, message, Snackbar.LENGTH_SHORT).show()
    }
}