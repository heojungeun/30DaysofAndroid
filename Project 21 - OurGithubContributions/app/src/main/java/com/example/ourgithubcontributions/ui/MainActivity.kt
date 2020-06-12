package com.example.ourgithubcontributions.ui

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.barryzhang.tcontributionsview.TContributionsView
import com.example.ourgithubcontributions.R
import com.example.ourgithubcontributions.retrofit.RetrofitPresenter
import com.example.ourgithubcontributions.SharedPreferenceManager
import com.example.ourgithubcontributions.data.CbRepositoryInjector
import com.example.ourgithubcontributions.data.model.User
import com.example.ourgithubcontributions.extension.toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var builder: AlertDialog.Builder
    private lateinit var dialogView: View
    private lateinit var dialogEditText: EditText
    private lateinit var dialog: AlertDialog

    private lateinit var adapter: UserListAdapter

    private lateinit var MyCbView: TContributionsView

    private val cbinstance = CbRepositoryInjector.getCbRepositoryImpl()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()

        loadTheirCb()

        setAlertDialog()
        setClickListener()
    }

    private fun init() {
        val myname = SharedPreferenceManager.token
        if (myname.isEmpty()) {
            toast("SharedPreference error")
            return
        } else {
            // my cb load
            MyCbView = findViewById<TContributionsView>(R.id.MainContributionView)
            RetrofitPresenter().getContributions(myname, MyCbView)
        }
    }

    private fun setAdapter(it: List<User>){
        adapter = UserListAdapter(it)
        //adapter.setList(it)
        val layoutManager = LinearLayoutManager(this)
        theirRcview.layoutManager = layoutManager
        theirRcview.adapter = adapter
    }

    private fun loadTheirCb() {
        cbinstance.getAll().observe(this, Observer {
            setAdapter(it)
        })
    }

    private fun setAlertDialog() {
        builder = AlertDialog.Builder(this)
        dialogView = layoutInflater.inflate(R.layout.dialog_add, null)
        dialogEditText = dialogView.findViewById<EditText>(R.id.dialogEdt)
        builder.setView(dialogView)
            .setPositiveButton("Add") { dialog: DialogInterface?, which: Int ->
                cbinstance.insertUser(User(dialogEditText.text.toString()))
            }
            .setNegativeButton("Cancel") { dialog: DialogInterface?, which: Int ->
                // nothing
            }
        dialog = builder.create()
    }

    private fun setClickListener() {
        btn_add.setOnClickListener {
            dialog.show()
        }
    }

}
