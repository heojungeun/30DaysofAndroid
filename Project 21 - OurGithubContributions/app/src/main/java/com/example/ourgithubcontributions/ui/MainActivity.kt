package com.example.ourgithubcontributions.ui

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.barryzhang.tcontributionsview.TContributionsView
import com.example.ourgithubcontributions.R
import com.example.ourgithubcontributions.Retrofit.RetrofitPresenter
import com.example.ourgithubcontributions.extension.toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var builder: AlertDialog.Builder
    private lateinit var dialogView: View
    private lateinit var dialogEditText: EditText
    private lateinit var dialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()

        setAlertDialog()
        setClickListener()
    }

    private fun init(){
        val MyCb = intent.getStringExtra("MyCb")
        if(MyCb != null){
            // room save
            sendUsername(MyCb)
        }else{
            // room get
        }
    }

    private fun setAlertDialog() {
        builder = AlertDialog.Builder(this)
        dialogView = layoutInflater.inflate(R.layout.dialog_add, null)
        dialogEditText = dialogView.findViewById<EditText>(R.id.dialogEdt)
        builder.setView(dialogView)
            .setPositiveButton("Add") { dialog: DialogInterface?, which: Int ->
                sendUsername(dialogEditText.text.toString())
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

    private fun sendUsername(edtStr: String) {
        val cbView = findViewById<TContributionsView>(R.id.MainContributionView)
        if (edtStr.isEmpty()) {
            toast("There is not userName")
            return
        }
        RetrofitPresenter().getContributions(edtStr, cbView)
    }

}
