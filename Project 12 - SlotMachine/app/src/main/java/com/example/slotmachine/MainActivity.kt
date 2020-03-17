package com.example.slotmachine

import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.GradientDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    var random = Random()
    var s1 = 0
    var s2 = 0
    var s3 = 0
    var coin = 10000
    private val imglist = arrayOf(
        R.drawable.sm1,
        R.drawable.sm2,
        R.drawable.sm3,
        R.drawable.sm4,
        R.drawable.sm5,
        R.drawable.sm6
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        spinbtn.setOnClickListener {

            coin = coin - 1000
            var plusstr = "Your coin : "+ coin.toString()
            coinview.text = plusstr

            slot1.setImageResource(R.drawable.move_anim)
            slot2.setImageResource(R.drawable.move_anim)
            slot3.setImageResource(R.drawable.move_anim)

            val s1anim = slot1.drawable as AnimationDrawable
            val s2anim = slot2.drawable as AnimationDrawable
            val s3anim = slot3.drawable as AnimationDrawable

            s1anim.start()
            s2anim.start()
            s3anim.start()

            var handler = Handler()
            handler.postDelayed({
                s1anim.stop()
                s2anim.stop()
                s3anim.stop()

                setImges()
                isjackpot()
            }, 1000)


            background_layout.setBackgroundColor(getColor(R.color.colorPrimary))

        }
    }

    private fun setImges(){
        s1 = random.nextInt(6)
        s2 = random.nextInt(6)
        s3 = random.nextInt(6)

        slot1.setImageResource(imglist[s1])
        slot2.setImageResource(imglist[s2])
        slot3.setImageResource(imglist[s3])
    }

    private fun isjackpot(){
        if ((s1==s2)&&(s2==s3)){
            randomColor()
            Snackbar.make(background_layout, "You got 100000!!",Snackbar.LENGTH_SHORT).show()
            coin = coin + 100000
        }
        else if((s1==s2)||(s2==s3)||(s1==s3)){
            Snackbar.make(background_layout, "You got 5000!!",Snackbar.LENGTH_SHORT).show()
            coin = coin + 5000
        }

        var plusstr = "Your coin : "+ coin.toString()
        coinview.text = plusstr
    }

    private fun randomColor(){
        val rnd = Random()
        val colors = IntArray(5)
        for (i in 0..4)
            colors[i] = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256),rnd.nextInt(256))

        val gd = GradientDrawable(GradientDrawable.Orientation.TL_BR, colors)

        gd.cornerRadius = 3f
        background_layout.background = gd
    }
}
