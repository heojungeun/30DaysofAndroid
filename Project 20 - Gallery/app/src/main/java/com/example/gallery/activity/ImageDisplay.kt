package com.example.gallery.activity

import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Fade
import com.example.gallery.R
import com.example.gallery.adapter.picture_Adpater
import com.example.gallery.fragment.pictureBrowserFragment
import com.example.gallery.utils.MarginDecoration
import com.example.gallery.utils.PicHolder
import com.example.gallery.utils.interfaces.itemClickListener
import com.example.gallery.utils.pictureFacer
import kotlinx.android.synthetic.main.activity_image_display.*


class ImageDisplay : AppCompatActivity(),
    itemClickListener {

    private lateinit var imageRecycler: RecyclerView
    private lateinit var allpictures: ArrayList<pictureFacer?>
    //var load: ProgressBar? = null
    private lateinit var foldePath: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_display)

        foldername.text = intent.getStringExtra("folderName")
        foldePath = intent.getStringExtra("folderPath")
        allpictures = ArrayList()

        imageRecycler = findViewById(R.id.recycler)
        imageRecycler.addItemDecoration(MarginDecoration(this))
        imageRecycler.hasFixedSize()

        // 처음엔 무조건 if 문에 들어감
        if (allpictures!!.isEmpty()) {
            loader.setVisibility(View.VISIBLE)
            allpictures = getAllImagesByFolder(foldePath)
            imageRecycler.adapter = picture_Adpater(allpictures, this@ImageDisplay, this)
            loader.setVisibility(View.GONE)
        }
    }

    /**
     *
     * @param holder The ViewHolder for the clicked picture
     * @param position The position in the grid of the picture that was clicked
     * @param pics An ArrayList of all the items in the Adapter
     */
    override fun onPicClicked(
        holder: PicHolder?,
        position: Int,
        pics: ArrayList<pictureFacer?>?
    ) {
        val browser = pictureBrowserFragment().newInstance(pics!!, position, this@ImageDisplay)

        // Note that we need the API version check here because the actual transition classes (e.g. Fade)
        // are not in the support library and are only available in API 21+. The methods we are calling on the Fragment
        // ARE available in the support library (though they don't do anything on API < 21)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //browser.setEnterTransition(new Slide());
            //browser.setExitTransition(new Slide()); uncomment this to use slide transition and comment the two lines below
            browser!!.setEnterTransition(Fade())
            browser.setExitTransition(Fade())
        }
        supportFragmentManager
            .beginTransaction()
            .addSharedElement(holder!!.picture!!, position.toString() + "picture")
            .add(R.id.displayContainer, browser!!)
            .addToBackStack(null)
            .commit()
    }

    override fun onPicClicked(
        pictureFolderPath: String?,
        folderName: String?
    ) {
    }

    /**
     * This Method gets all the images in the folder paths passed as a String to the method and returns
     * and ArrayList of pictureFacer a custom object that holds data of a given image
     * @param path a String corresponding to a folder path on the device external storage
     */
    fun getAllImagesByFolder(path: String): ArrayList<pictureFacer?> {
        var images: ArrayList<pictureFacer?> = ArrayList()
        val allVideosuri: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(
            MediaStore.Images.ImageColumns.DATA, MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.SIZE
        )
        val cursor: Cursor? = this@ImageDisplay.contentResolver.query(
            allVideosuri,
            projection,
            MediaStore.Images.Media.DATA + " like ? ",
            arrayOf("%$path%"),
            null
        )
        try {
            cursor!!.moveToFirst()
            do {
                val pic = pictureFacer()
                pic.setPicturName(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)))
                pic.setPicturePath(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)))
                pic.setPictureSize(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE)))
                images.add(pic)
            } while (cursor.moveToNext())
            cursor.close()
            val reSelection: ArrayList<pictureFacer?> = ArrayList()
            for (i in images.size - 1 downTo -1 + 1) {
                reSelection.add(images[i]!!)
            }
            images = reSelection
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return images
    }
}