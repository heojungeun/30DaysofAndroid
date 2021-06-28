package com.example.firstfcm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import com.google.firebase.messaging.FirebaseMessaging
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {

    private val resultTextView: TextView by lazy {
        findViewById<TextView>(R.id.resultTextView)
    }

    private val firebaseTokenTextView: TextView by lazy {
        findViewById<TextView>(R.id.firebaseTokenTextView)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initFirebase()
    }

    private fun initFirebase() {
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener { task ->
                if(task.isSuccessful) {
                    firebaseTokenTextView.text = task.result
                }
            }
    }
}