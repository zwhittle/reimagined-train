package com.awesome.zach.jotunheimrsandbox

import com.awesome.zach.jotunheimrsandbox.utils.Utils
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import java.time.LocalDate
import java.util.*

class UtilsTests {

    @Test
    @Throws(Exception::class)
    fun firstDayOfThisWeekTest() {
        val correctFirstDayOfThisWeek = LocalDate.parse("2019-02-17")
        val utilSays = Utils.firstDayOfThisWeek(Locale.US)

        assertThat(utilSays, equalTo(correctFirstDayOfThisWeek))
    }

    @Test
    @Throws(Exception::class)
    fun lastDayOfThisWeekTest() {
        val correctLastDayOfThisWeek = LocalDate.parse("2019-02-23")
        val utilSays = Utils.lastDayOfThisWeek(Locale.US)

        assertThat(utilSays, equalTo(correctLastDayOfThisWeek))
    }
}