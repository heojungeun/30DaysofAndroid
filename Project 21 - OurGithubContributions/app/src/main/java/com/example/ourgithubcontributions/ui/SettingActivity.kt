package com.example.ourgithubcontributions.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ourgithubcontributions.R
import com.example.ourgithubcontributions.SharedPreferenceManager
import com.example.ourgithubcontributions.data.model.User
import com.example.ourgithubcontributions.extension.toast
import com.example.ourgithubcontributions.ui.adapter.DelListAdapter
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : AppCompatActivity() {

    private lateinit var Myname: String
    private lateinit var del_rcview: RecyclerView
    private lateinit var madapter: DelListAdapter

    private val vm: UserViewModel = UserViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        init()
        setClickListener()
    }

    private fun init() {
        Myname = SharedPreferenceManager.token

        del_rcview = findViewById(R.id.pre_rcview_del)
        vm.userList.observe(this, Observer {
            setAdapter(it)
        })
    }

    private fun setAdapter(it: List<User>) {
        madapter = DelListAdapter(it)
        val layoutManager = LinearLayoutManager(this)
        del_rcview.layoutManager = layoutManager
        del_rcview.adapter = madapter
    }

    private fun setClickListener() {
        btn_back.setOnClickListener {
            finish()
        }

        btn_del.setOnClickListener {
            var res = madapter.getCheckedList()
            when (res.size) {
                0 -> toast("Select user")
                1 -> vm.delete(User(res[0], true))
                madapter.itemCount -> vm.deleteAll()
                else -> vm.deleteList(res)
            }
        }

        btn_logout.setOnClickListener {
            if (logout_edt.text.toString() == Myname) {
                SharedPreferenceManager.token = ""
                vm.deleteAll()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            } else {
                toast("Please type your username")
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        var imm: InputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        return true
    }
}