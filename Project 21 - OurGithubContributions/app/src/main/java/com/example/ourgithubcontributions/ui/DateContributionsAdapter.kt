package com.example.ourgithubcontributions.ui

/**
 * https://github.com/barryhappy
 * Created by Barry on 2016/11/20
 *
 * Edited by heojungeun 2020/06/14
 * Comment line 42
 */

import android.util.Log
import com.barryzhang.tcontributionsview.adapter.BaseContributionsViewAdapter
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class DateContributionsAdapter : BaseContributionsViewAdapter() {
    var weekCount = 15
    var endDay = Calendar.getInstance(locale)
    private val map: MutableMap<String, Int> =
        HashMap()

    override fun getRowCount(): Int {
        return 7
    }

    override fun getColumnCount(): Int {
        return weekCount
    }

    override fun getLevel(row: Int, column: Int): Int {
        val calendar = computeDay(row, column)
        if (calendar.timeInMillis > endDay.timeInMillis &&
            calendar[Calendar.DAY_OF_YEAR] != endDay[Calendar.DAY_OF_YEAR]
        ) {
            // This day is after endDay;
            return -1
        }
        val dateKey =
            SimpleDateFormat("yyyy-MM-dd").format(calendar.time)
        //Log.d("dateKey", dateKey)
        return if (map.containsKey(dateKey)) {
            map[dateKey]!!
        } else 0
    }

    fun clear() {
        map.clear()
    }

    fun put(date: String, level: Int) {
        map[mapDate(date)] = level
    }

    private fun mapDate(date: String): String {
        return date
    }

    private val locale: Locale
        get() = Locale.getDefault()

    private fun computeDay(row: Int, column: Int): Calendar {
        val startDay = computeStartDay()
        startDay[Calendar.DAY_OF_YEAR] = startDay[Calendar.DAY_OF_YEAR] + column * 7 + row
        return startDay
    }

    private fun computeStartDay(): Calendar {
        val startDay = Calendar.getInstance(locale)
        startDay.time = endDay.time
        var dayToNextSunDay = 0
        when (startDay[Calendar.DAY_OF_WEEK]) {
            Calendar.SUNDAY -> dayToNextSunDay = 7
            Calendar.MONDAY -> dayToNextSunDay = 6
            Calendar.TUESDAY -> dayToNextSunDay = 5
            Calendar.WEDNESDAY -> dayToNextSunDay = 4
            Calendar.THURSDAY -> dayToNextSunDay = 3
            Calendar.FRIDAY -> dayToNextSunDay = 2
            Calendar.SATURDAY -> dayToNextSunDay = 1
        }
        startDay.timeInMillis =
            endDay.timeInMillis - (weekCount * 7 - dayToNextSunDay) * TIME_OF_DAY
        return startDay
    }

    fun setEndDay(mEndDayString: String?): Boolean {
        val df: DateFormat =
            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        try {
            endDay.time = df.parse(mEndDayString)
        } catch (e: ParseException) {
            e.printStackTrace()
            return false
        }
        return true
    }

    companion object {
        private const val TIME_OF_DAY = 24 * 60 * 60 * 1000L
    }
}