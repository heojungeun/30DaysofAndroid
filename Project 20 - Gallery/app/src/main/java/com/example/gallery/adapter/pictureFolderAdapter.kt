package com.example.gallery.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.gallery.R
import com.example.gallery.utils.imageFolder
import com.example.gallery.utils.interfaces.itemClickListener


class pictureFolderAdapter : RecyclerView.Adapter<pictureFolderAdapter.FolderHolder> {
    private var folders: ArrayList<imageFolder>? = null
    private var folderContx: Context? = null
    private var listenToClick: itemClickListener? = null

    /**
     *
     * @param folders An ArrayList of String that represents paths to folders on the external storage that contain pictures
     * @param folderContx The Activity or fragment Context
     * @param listen interFace for communication between adapter and fragment or activity
     */
    constructor(
        folders: ArrayList<imageFolder>?,
        folderContx: Context?,
        listen: itemClickListener?
    ) {
        this.folders = folders
        this.folderContx = folderContx
        listenToClick = listen
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FolderHolder {
        val inflater = LayoutInflater.from(parent.context)
        val cell: View = inflater.inflate(R.layout.picture_folder_item, parent, false)
        return FolderHolder(cell)
    }

    override fun onBindViewHolder(holder: FolderHolder, position: Int) {
        val folder = folders!![position]
        Glide.with(folderContx!!)
            .load(folder.getFirstPic())
            .apply(RequestOptions().centerCrop())
            .into(holder.folderPic)
        val text = "(" + folder.getNumberOfPics() + ") " + folder.getFolderName()
        holder.folderName.text = text
        holder.folderPic.setOnClickListener{
            fun onClick(v: View?) {
                listenToClick!!.onPicClicked(folder.getPath(), folder.getFolderName())
            }
        }
    }

    override fun getItemCount(): Int {
        return folders!!.size
    }


    class FolderHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var folderPic: ImageView
        var folderName: TextView
        var folderCard: CardView

        init {
            folderPic = itemView.findViewById(R.id.folderPic)
            folderName = itemView.findViewById(R.id.folderName)
            folderCard = itemView.findViewById(R.id.folderCard)
        }
    }
}