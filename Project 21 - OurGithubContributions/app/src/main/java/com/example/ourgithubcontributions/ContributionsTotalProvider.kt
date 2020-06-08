package com.example.ourgithubcontributions

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetManager.ACTION_APPWIDGET_UPDATE
import android.appwidget.AppWidgetProvider
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.TextUtils
import android.util.Log
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.example.ourgithubcontributions.contributions.ContributionsInterface
import com.example.ourgithubcontributions.contributions.ContributionsModel
import com.example.ourgithubcontributions.contributions.ContributionsPresenter
import com.example.ourgithubcontributions.data.ContributionsDay
import com.example.ourgithubcontributions.data.DBHelper

class ContributionsTotalProvider : AppWidgetProvider() {

    val TAG = "ContributionsTotalProvider"

    val mModel = ContributionsModel()
    lateinit var mPresenter: ContributionsInterface.Presenter

    override fun onReceive(context: Context?, intent: Intent?) {

        Log.d(TAG, "onReceive ${intent?.action}")

        if (context == null) return

        if(intent?.action == ACTION_APPWIDGET_UPDATE){
            val userName = mModel.getUserName(context)
            if(!TextUtils.isEmpty(userName)){
                mPresenter = ContributionsPresenter(object : ContributionsInterface.View {
                    override fun showFailure(msg: String?) {
                        Log.e(TAG, msg)
                    }

                    override fun showContributions(list: List<ContributionsDay>) {
                        val uri = Uri.parse("content://${context.applicationInfo?.packageName}.providers/${DBHelper.TABLE_NAME}")
                        list.forEach {
                            val values = ContentValues()
                            values.put(DBHelper.COLUMN_COLOR, it.color)
                            values.put(DBHelper.COLUMN_DATA_COUNT, it.dataCount)
                            values.put(DBHelper.COLUMN_DATE, it.day)
                            context.contentResolver.insert(uri, values)
                        }
                        super@ContributionsTotalProvider.onReceive(context, intent)
                    }
                }, mModel)
                mPresenter.initUserContributions(userName)
                return
            }
        }

        super.onReceive(context, intent)
    }

    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?
    ) {

        val rvWidget = RemoteViews(context?.packageName, R.layout.grid_contributions)
        val intent = Intent(context, GridRemoteViewsService::class.java)
        rvWidget.setRemoteAdapter(R.id.rgridView, intent)
        appWidgetManager?.updateAppWidget(appWidgetIds, rvWidget)

        super.onUpdate(context, appWidgetManager, appWidgetIds)
    }
}

class GridRemoteViewsService : RemoteViewsService(){
    override fun onGetViewFactory(intent: Intent?): RemoteViewsFactory {
        return GridViewsAdapter(this)
    }
}