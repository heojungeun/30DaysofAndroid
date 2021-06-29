package com.example.firstfcm

import android.content.Intent
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
        updateResult()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        setIntent(intent)
        updateResult(true)
    }

    private fun initFirebase() {
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener { task ->
                if(task.isSuccessful) {
                    firebaseTokenTextView.text = task.result
                }
            }
    }

    private fun updateResult(isNewIntent: Boolean = false){
        resultTextView.text = (intent.getStringExtra("notificationType") ?: "앱 런처") +
            if(isNewIntent){
                "(으)로 갱신했습니다."
            } else {
                "(으)로 실행했습니다."
            }
    }
}