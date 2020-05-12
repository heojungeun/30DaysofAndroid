package com.example.gallery.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.gallery.R
import com.example.gallery.adapter.pictureFolderAdapter
import com.example.gallery.utils.MarginDecoration
import com.example.gallery.utils.PicHolder
import com.example.gallery.utils.imageFolder
import com.example.gallery.utils.interfaces.itemClickListener
import com.example.gallery.utils.pictureFacer
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), itemClickListener{

    private lateinit var foldercv: RecyclerView
    private val MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE
            )
        }
        foldercv = findViewById(R.id.folderRecycler)
        foldercv.addItemDecoration(MarginDecoration(this))
        foldercv.hasFixedSize()
        val folds = getPicturePaths()

        if (folds!!.isEmpty()) {
            empty_text.visibility = View.VISIBLE
        } else {
            val folderAdapter: RecyclerView.Adapter<*> =
                pictureFolderAdapter(folds, this@MainActivity, this)
            folderRecycler.adapter = folderAdapter
        }

    }

    private fun getPicturePaths(): ArrayList<imageFolder>? {
        val picFolders: ArrayList<imageFolder> = ArrayList()
        val picPaths: ArrayList<String> = ArrayList()
        val allImagesuri: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(
            MediaStore.Images.ImageColumns.DATA, MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME, MediaStore.Images.Media.BUCKET_ID
        )
        val cursor: Cursor? =
            this.contentResolver.query(allImagesuri, projection, null, null, null)
        try {
            if (cursor != null) {
                cursor.moveToFirst()
            }
            do {
                val folds = imageFolder()
                val name: String =
                    cursor!!.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME))
                val folder: String =
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME))
                val datapath: String =
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))

                //String folderpaths =  datapath.replace(name,"");
                var folderpaths =
                    datapath.substring(0, datapath.lastIndexOf("$folder/"))
                folderpaths = "$folderpaths$folder/"
                if (!picPaths.contains(folderpaths)) {
                    picPaths.add(folderpaths)
                    folds.setPath(folderpaths)
                    folds.setFolderName(folder)
                    folds.setFirstPic(datapath) //if the folder has only one picture this line helps to set it as first so as to avoid blank image in itemview
                    folds.addpics()
                    picFolders.add(folds)
                } else {
                    for (i in 0 until picFolders.size) {
                        if (picFolders[i].getPath().equals(folderpaths)) {
                            picFolders[i].setFirstPic(datapath)
                            picFolders[i].addpics()
                        }
                    }
                }
            } while (cursor!!.moveToNext())
            cursor.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        for (i in 0 until picFolders.size) {
            Log.d(
                "picture folders",
                picFolders[i].getFolderName().toString() + " and path = " + picFolders[i]
                    .getPath() + " " + picFolders[i].getNumberOfPics()
            )
        }

        //reverse order ArrayList
        /* ArrayList<imageFolder> reverseFolders = new ArrayList<>();
        for(int i = picFolders.size()-1;i > reverseFolders.size()-1;i--){
            reverseFolders.add(picFolders.get(i));
        }*/return picFolders
    }

    override fun onPicClicked(
        holder: PicHolder?,
        position: Int,
        pics: ArrayList<pictureFacer?>?
    ) {
    }

    /**
     * Each time an item in the RecyclerView is clicked this method from the implementation of the transitListerner
     * in this activity is executed, this is possible because this class is passed as a parameter in the creation
     * of the RecyclerView's Adapter, see the adapter class to understand better what is happening here
     * @param pictureFolderPath a String corresponding to a folder path on the device external storage
     */
    override fun onPicClicked(
        pictureFolderPath: String?,
        folderName: String?
    ) {
        val move = Intent(this@MainActivity, ImageDisplay::class.java)
        move.putExtra("folderPath", pictureFolderPath)
        move.putExtra("folderName", folderName)

        //move.putExtra("recyclerItemSize",getCardsOptimalWidth(4));
        startActivity(move)
    }
}
