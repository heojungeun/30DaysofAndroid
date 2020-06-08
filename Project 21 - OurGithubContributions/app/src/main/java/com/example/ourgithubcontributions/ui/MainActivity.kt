package com.example.ourgithubcontributions.ui

import android.app.Activity
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.ourgithubcontributions.ContributionsTotalProvider
import com.example.ourgithubcontributions.Converter
import com.example.ourgithubcontributions.R
import com.example.ourgithubcontributions.contributions.ContributionsInterface
import com.example.ourgithubcontributions.data.ContributionsDay
import com.example.ourgithubcontributions.contributions.ContributionsModel
import com.example.ourgithubcontributions.contributions.ContributionsPresenter
import com.example.ourgithubcontributions.data.DBHelper
import com.example.ourgithubcontributions.data.DBHelper.Companion.TABLE_NAME
import com.example.ourgithubcontributions.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), ContributionsInterface.View {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        val model = ContributionsModel()

        val name = model.getUserName(this)
        binding.editUsername.setText(name)
        binding.editUsername.setSelection(name.length)

        val mPresenter = ContributionsPresenter(this, model)

        binding.btnUpdate.setOnClickListener {
            val userName = binding.editUsername.text.toString()
            if (userName.isEmpty()){
                toast("User Name can't be blank")
                return@setOnClickListener
            }
            model.saveUserName(this, userName)
            mPresenter.initUserContributions(userName)
        }
    }

    private fun toast(response: String){
        Toast.makeText(this,response,Toast.LENGTH_SHORT).show()
    }

    override fun showContributions(list: List<ContributionsDay>){

        val uri = Uri.parse("content://${applicationInfo.packageName}.providers/$TABLE_NAME")
        list.forEach {
            val values = ContentValues()
            values.put(DBHelper.COLUMN_COLOR, it.color)
            values.put(DBHelper.COLUMN_DATA_COUNT, it.dataCount)
            values.put(DBHelper.COLUMN_DATE, it.day)
            contentResolver.insert(uri, values)
        }

        updateContributionsList()

        finish()
    }

    override fun showFailure(msg: String?) {
        runOnUiThread {
            if (msg != null)
                toast(msg)
            else
                toast("Fail server connect")
        }
    }

    private fun updateContributionsList(){
        val name = ComponentName(this, ContributionsTotalProvider::class.java)
        val widgetIds = AppWidgetManager.getInstance(this).getAppWidgetIds(name)
        val updateWidgetListIntent = Intent(this, ContributionsTotalProvider::class.java)

        updateWidgetListIntent.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
        updateWidgetListIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, widgetIds)
        updateWidgetListIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetIds.last())
        sendBroadcast(updateWidgetListIntent)
        setResult(Activity.RESULT_OK,updateWidgetListIntent)
    }

}
