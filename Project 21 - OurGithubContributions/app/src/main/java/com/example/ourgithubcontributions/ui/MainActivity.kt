package com.example.ourgithubcontributions.ui

import android.content.ComponentName
import android.content.ContentValues
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.ourgithubcontributions.Converter
import com.example.ourgithubcontributions.R
import com.example.ourgithubcontributions.contributions.ContributionsInterface
import com.example.ourgithubcontributions.data.ContributionsDay
import com.example.ourgithubcontributions.contributions.ContributionsModel
import com.example.ourgithubcontributions.data.DBHelper
import com.example.ourgithubcontributions.data.DBHelper.Companion.TABLE_NAME
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), ContributionsInterface.View {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }


    fun toast(mode: Int, response: String){
        when(mode){
            0 -> Toast.makeText(this@MainActivity,"Fail server connect",Toast.LENGTH_SHORT).show()
            1 -> Toast.makeText(this@MainActivity,response,Toast.LENGTH_SHORT).show()
        }
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

    }

    override fun showFailure(msg: String?) {
        runOnUiThread {
            if (msg != null)
                toast(1, msg)
            else
                toast(0,"")
        }
    }

    private fun updateContributionsList(){

    }

}
