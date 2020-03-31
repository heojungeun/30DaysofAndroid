package com.example.firstmlkit

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addbtn.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            val dialogView = layoutInflater.inflate(R.layout.activity_add_dialog, null)
            val dialogurl = dialogView.findViewById<EditText>(R.id.dialogEdt)

            builder.setView(dialogView)
                .setPositiveButton("확인") { dialogInterface, i ->
                    // 받은 url을 imgview에 적용한다.
                    Glide.with(this)
                        .load(dialogurl.text.toString())
                        .into(mlimgview)
                }
                .setNegativeButton("취소") {dialogInterface, i ->
                    // 취소 시 아무것도 하지 않음
                }

            val dialog = builder.create()
            dialog.show()
            dialog.window?.setBackgroundDrawableResource(R.drawable.whiteborder)
        }

        mltextbtn.setOnClickListener {
            val textImg = FirebaseVisionImage.fromBitmap(
                (mlimgview.drawable as BitmapDrawable).bitmap
            )

            val detector = FirebaseVision.getInstance().onDeviceTextRecognizer

            detector.processImage(textImg)
                .addOnCompleteListener {
                    var detectedText = "* "
                    it.result?.textBlocks?.forEach{
                        detectedText += it.text + "\n"
                    }
                    runOnUiThread {
                        imgcnt.text = detectedText
                    }
                }
                .addOnFailureListener { e->
                    // print mlcnt "i dont know"
                }
        } // https://live.staticflickr.com/5602/15552633902_62cafb7fa9_c.jpg

        mlfacebtn.setOnClickListener {
            val detector = FirebaseVision.getInstance().visionFaceDetector

            detector.detectInImage(FirebaseVisionImage.fromBitmap(
                    (mlimgview.drawable as BitmapDrawable).bitmap
                )).addOnCompleteListener {
                    var markedBitmap =
                        (mlimgview.drawable as BitmapDrawable)
                            .bitmap
                            .copy(Bitmap.Config.ARGB_8888, true)
                    val canvas = Canvas(markedBitmap)
                    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
                    paint.color = Color.parseColor("#99D7591A")

                    it.result?.forEach {
                        canvas.drawRect(it.boundingBox, paint)
                    }
                    runOnUiThread {
                        mlimgview.setImageBitmap(markedBitmap)
                    }
                }
                .addOnFailureListener { e->
                    // print mlcnt "i dont know"
                }
        }

        mllabelbtn.setOnClickListener {
            val labelImg = FirebaseVisionImage.fromBitmap(
                (mlimgview.drawable as BitmapDrawable).bitmap
            )

            val detector = FirebaseVision.getInstance().getCloudImageLabeler()

            detector.processImage(labelImg)
                .addOnSuccessListener { labels ->
                    var output = "* "
                    for (label in labels) {
                        if (label.confidence > 0.7)
                            output += label.text + "\n"
                    }

                    runOnUiThread {
                        imgcnt.text = output
                    }
                }
                .addOnFailureListener { e->
                    // print mlcnt "i dont know"
                }
        }

    }
}
