@file:OptIn(kotlinx.cinterop.ExperimentalForeignApi::class)

package io.github.composefluent.component

import kotlinx.cinterop.UShortVar
import kotlinx.cinterop.allocArray
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.toKStringFromUtf16
import platform.windows.GetLocaleInfoW
import platform.windows.GetUserDefaultLCID
import platform.windows.LOCALE_IFIRSTDAYOFWEEK
import platform.windows.LOCALE_SABBREVDAYNAME1
import platform.windows.LOCALE_SABBREVMONTHNAME1

internal actual fun getLocalDayOfWeekNames(): List<String> = List(7) { day ->
    // Windows starts its locale day constants at Monday; CalendarView starts at Sunday.
    localeString(LOCALE_SABBREVDAYNAME1.toUInt() + ((day + 6) % 7).toUInt())
}

internal actual fun getLocalMonthNames(): List<String> = List(12) { month ->
    localeString(LOCALE_SABBREVMONTHNAME1.toUInt() + month.toUInt())
}

internal actual fun getLocalFirstDayOfWeek(): Int =
    (localeString(LOCALE_IFIRSTDAYOFWEEK.toUInt()).toInt() + 1) % 7 + 1

private fun localeString(type: UInt): String = memScoped {
    val locale = GetUserDefaultLCID()
    val length = GetLocaleInfoW(locale, type, null, 0)
    check(length > 0) { "Windows locale field $type is unavailable" }
    val buffer = allocArray<UShortVar>(length)
    check(GetLocaleInfoW(locale, type, buffer, length) > 0)
    buffer.toKStringFromUtf16()
}
