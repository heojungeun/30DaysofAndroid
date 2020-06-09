package com.example.ourgithubcontributions

import android.graphics.Color
import com.example.ourgithubcontributions.data.ContributionsDay
import java.util.regex.Pattern

object Converter {

    @JvmStatic
    fun svgToContributionsList(svg: String): List<ContributionsDay> {
        val contributionsList = ArrayList<ContributionsDay>(52 * 7)

        // ex) fill="#c6e48b" data-count="3" data-date="2020-02-26"
        val matcher = Pattern.compile(
            """"(#[A-Za-z0-9]{6})"""" +
                    """ data-count="([\d]+)"""" +
                    """ data-date="([\d]{4}-[\d]{2}-[\d]{1,2})""""
        ).matcher(svg)

        while (matcher.find()) {
            //val color = Color.parseColor(matcher.group(1))
            val color = matcher.group(1).toString()
            val dataCount = Integer.parseInt(matcher.group(2))
            val day = matcher.group(3).toString()

            val contributionTmp =
                ContributionsDay(
                    day,
                    dataCount,
                    color
                )
            contributionsList.add(contributionTmp)
        }

        return contributionsList
    }
}