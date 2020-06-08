package com.example.ourgithubcontributions

import android.content.Context
import android.net.Uri
import android.widget.RemoteViews
import android.widget.RemoteViewsService.RemoteViewsFactory
import com.example.ourgithubcontributions.data.ContributionsDay
import com.example.ourgithubcontributions.data.DBHelper.Companion.COLUMN_COLOR
import com.example.ourgithubcontributions.data.DBHelper.Companion.COLUMN_DATA_COUNT
import com.example.ourgithubcontributions.data.DBHelper.Companion.COLUMN_DATE
import com.example.ourgithubcontributions.data.DBHelper.Companion.TABLE_NAME
import java.util.*
import kotlin.collections.ArrayList

class GridViewsAdapter(val context: Context) : RemoteViewsFactory {

    val TAG = "GridRemoteViewsAdapter"
    val WIDTH = 26
    val HEIGHT = 7
    val TOTALSIZE = WIDTH * HEIGHT
    val uri = Uri.parse("content://${context.applicationInfo?.packageName}.provider/$TABLE_NAME")
    val array = ArrayList<ArrayList<ContributionsDay>>(HEIGHT)

    override fun getCount(): Int = TOTALSIZE

    override fun getLoadingView(): RemoteViews =
        RemoteViews(context.packageName, R.layout.grid_cell)

    override fun getViewAt(position: Int): RemoteViews {
        val rv = RemoteViews(context.packageName, R.layout.grid_cell)

        if (array.isEmpty())
            populateCursor()

        val row = position / WIDTH
        val col = position % WIDTH

        if (col < array[row].size) {
            val contributionsTmp = array[row][col]
            rv.setInt(R.id.view, "setBackgroundColor", contributionsTmp.color)
        }

        return rv
    }

    override fun getViewTypeCount(): Int = 1

    override fun hasStableIds(): Boolean = true

    override fun getItemId(position: Int): Long = position.toLong()

    override fun onCreate() {
        populateCursor()
    }

    override fun onDataSetChanged() {
        populateCursor()
    }

    override fun onDestroy() {
        array.clear()
    }

    private fun populateCursor() {
        var total = TOTALSIZE
        val mCursor = context.contentResolver.query(
            uri,
            null,
            null,
            null,
            "$COLUMN_DATE DESC limit $total"
        )

        if (mCursor?.moveToFirst() as Boolean){
            val date = mCursor.getInt(mCursor.getColumnIndex(COLUMN_DATE))
            val year = date / 10000
            val month = date % 10000 / 100
            val day = date % 100
            val calendar = Calendar.getInstance()
            calendar.set(year, month - 1, day) // month is 0~

            val week = calendar.get(Calendar.DAY_OF_WEEK)
            total -= (HEIGHT - week)

            if (mCursor.moveToPosition(total - 1)){
                array.clear()

                (1..HEIGHT).forEach {
                    array.add(ArrayList<ContributionsDay>(WIDTH))
                }
                var curWeek = HEIGHT - 1

                do {
                    curWeek %= HEIGHT
                    array[HEIGHT - 1 - curWeek].add(ContributionsDay(
                        "${mCursor.getInt(mCursor.getColumnIndex(COLUMN_DATE))}",
                        mCursor.getInt(mCursor.getColumnIndex(COLUMN_DATA_COUNT)),
                        mCursor.getInt(mCursor.getColumnIndex(COLUMN_COLOR))
                    ))
                    curWeek = curWeek + HEIGHT - 1
                }while (mCursor.moveToPrevious())
            }
        }

        mCursor.close()
    }

}