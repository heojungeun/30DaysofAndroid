package com.example.timepatterntimer

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.timepatterntimer.databinding.ActivitySetupBinding
import kotlinx.android.synthetic.main.activity_setup.*

class SetupActivity : AppCompatActivity() {

    private val vm = StateViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setup)

        onClickListener()
    }

    private fun onClickListener(){
        btn_back.setOnClickListener {
            finish()
        }

        btn_save.setOnClickListener {

        }

        btn_break.setOnClickListener {
            val bTimeId = radioGroup.checkedRadioButtonId
            if (bTimeId == -1){
                Toast.makeText(this, "check break time", Toast.LENGTH_SHORT).show()
            }else{
                var bTime = when(bTimeId){
                    R.id.rb_1 -> 1
                    R.id.rb_5 -> 5
                    R.id.rb_10 -> 10
                    else -> 15
                }
                vm.setBreak(bTime)
            }
        }

        btn_pat.setOnClickListener {
            val patStr = pre_pat_edt.text.toString()
            if (patStr.isEmpty()){
                Toast.makeText(this, "input pattern number", Toast.LENGTH_SHORT).show()
            }else{
                vm.setPattern(patStr.toInt())
            }
        }

    }
}