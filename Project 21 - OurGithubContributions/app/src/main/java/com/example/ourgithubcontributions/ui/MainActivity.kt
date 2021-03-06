package com.example.ourgithubcontributions.ui

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.barryzhang.tcontributionsview.TContributionsView
import com.example.ourgithubcontributions.R
import com.example.ourgithubcontributions.retrofit.RetrofitPresenter
import com.example.ourgithubcontributions.SharedPreferenceManager
import com.example.ourgithubcontributions.data.model.User
import com.example.ourgithubcontributions.extension.toast
import com.example.ourgithubcontributions.ui.adapter.UserListAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var builder: AlertDialog.Builder
    private lateinit var dialogView: View
    private lateinit var dialogEditText: EditText
    private lateinit var dialog: AlertDialog

    private lateinit var MyCbView: TContributionsView
    private lateinit var mtheirRcview: RecyclerView
    private lateinit var madapter: UserListAdapter

    private val vm: UserViewModel = UserViewModel()

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
            MyCbView = findViewById(R.id.MainContributionView)
            RetrofitPresenter().getContributions(myname, MyCbView)

            mtheirRcview = findViewById(R.id.theirRcview)
        }
    }

    private fun loadTheirCb() {
        vm.userList.observe(this, Observer {
            setAdapter(it)
        })
    }

    private fun setAdapter(it: List<User>) {
        madapter = UserListAdapter(it)
        val layoutManager = LinearLayoutManager(this)
        mtheirRcview.layoutManager = layoutManager
        mtheirRcview.adapter = madapter
    }

    private fun setAlertDialog() {
        builder = AlertDialog.Builder(this)
        dialogView = layoutInflater.inflate(R.layout.dialog_add, null)
        dialogEditText = dialogView.findViewById<EditText>(R.id.dialogEdt)
        builder.setView(dialogView)
            .setPositiveButton("Add") { dialog: DialogInterface?, which: Int ->
                vm.insertUser(User(dialogEditText.text.toString(), false))
                dialogEditText.setText("")
            }
            .setNegativeButton("Cancel") { dialog: DialogInterface?, which: Int ->
                dialogEditText.setText("")
            }
            .setCancelable(false)
        dialog = builder.create()
    }

    private fun setClickListener() {
        btn_add.setOnClickListener {
            dialog.show()
        }

        btn_set.setOnClickListener {
            val intent = Intent(this, SettingActivity::class.java)
            startActivity(intent)
        }
    }

}
