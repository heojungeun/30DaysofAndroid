package com.example.gallery.fragment


import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.core.view.ViewCompat.setTransitionName
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.gallery.R
import com.example.gallery.adapter.recyclerViewPagerImageIndicator
import com.example.gallery.utils.interfaces.imageIndicatorListener
import com.example.gallery.utils.pictureFacer
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import kotlinx.android.synthetic.main.dialog_viewer.*
import kotlinx.android.synthetic.main.picture_browser.*


class pictureBrowserFragment: Fragment, imageIndicatorListener{
    private var allImages: ArrayList<pictureFacer?> = ArrayList()
    private var position = 0
    private var animeContx: Context? = null
    private var imagePager: ViewPager? = null
    private var indicatorRecycler: RecyclerView? = null
    private var viewVisibilityController = 0
    private var viewVisibilitylooper = 0
    private var pagingImages: ImagesPagerAdapter? = null
    private var previousSelected = -1

    constructor()

    constructor(
        allImages: ArrayList<pictureFacer?>,
        imagePosition: Int,
        anim: Context?
    ) {
        this.allImages = allImages
        position = imagePosition
        animeContx = anim
    }

    fun newInstance(
        allImages: ArrayList<pictureFacer?>,
        imagePosition: Int,
        anim: Context?
    ): pictureBrowserFragment? {
        return pictureBrowserFragment(allImages, imagePosition, anim)
    }


    @Nullable
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.picture_browser, container, false)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, @Nullable savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /**
         * initialisation of the recyclerView visibility control integers
         */
        viewVisibilityController = 0
        viewVisibilitylooper = 0
        /**
         * setting up the viewPager with images
         */
        imagePager = view.findViewById(R.id.imagePager)
        pagingImages = ImagesPagerAdapter()
        imagePager!!.adapter = pagingImages
        imagePager!!.offscreenPageLimit = 3
        imagePager!!.currentItem =
            position //displaying the image at the current position passed by the ImageDisplay Activity
        /**
         * setting up the recycler view indicator for the viewPager
         */
        indicatorRecycler = view.findViewById(R.id.indicatorRecycler)
        indicatorRecycler!!.hasFixedSize()
        indicatorRecycler!!.layoutManager = GridLayoutManager(
            context,
            1,
            RecyclerView.HORIZONTAL,
            false
        )
        val indicatorAdapter: RecyclerView.Adapter<*> =
            recyclerViewPagerImageIndicator(allImages, context, this)
        indicatorRecycler!!.adapter = indicatorAdapter

        //adjusting the recyclerView indicator to the current position of the viewPager, also highlights the image in recyclerView with respect to the
        //viewPager's position
        allImages[position]!!.setSelected(true)
        previousSelected = position
        indicatorAdapter.notifyDataSetChanged()
        indicatorRecycler!!.scrollToPosition(position)
        /**
         * this listener controls the visibility of the recyclerView
         * indication and it current position in respect to the image ViewPager
         */
        imagePager!!.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                if (previousSelected != -1) {
                    allImages[previousSelected]!!.setSelected(false)
                    previousSelected = position
                    allImages[position]!!.setSelected(true)
                    indicatorRecycler!!.adapter!!.notifyDataSetChanged()
                    indicatorRecycler!!.scrollToPosition(position)
                } else {
                    previousSelected = position
                    allImages[position]!!.setSelected(true)
                    indicatorRecycler!!.adapter!!.notifyDataSetChanged()
                    indicatorRecycler!!.scrollToPosition(position)
                }
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
        indicatorRecycler!!.setOnTouchListener { v, event ->
            /**
             * uncomment the below condition to control recyclerView visibility automatically
             * when image is clicked also uncomment the condition set on the image's onClickListener in the ImagesPagerAdapter adapter
             */
            /**
             * uncomment the below condition to control recyclerView visibility automatically
             * when image is clicked also uncomment the condition set on the image's onClickListener in the ImagesPagerAdapter adapter
             */

            /*if(viewVisibilityController == 0){
                            indicatorRecycler.setVisibility(View.VISIBLE);
                            visibiling();
                        }else{
                            viewVisibilitylooper++;
                        }*/
            false
        }
    }


    /**
     * this method of the imageIndicatorListerner interface helps in communication between the fragment and the recyclerView Adapter
     * each time an iten in the adapter is clicked the position of that item is communicated in the fragment and the position of the
     * viewPager is adjusted as follows
     * @param ImagePosition The position of an image item in the RecyclerView Adapter
     */
    override fun onImageIndicatorClicked(ImagePosition: Int) {

        //the below lines of code highlights the currently select image in  the indicatorRecycler with respect to the viewPager position
        if (previousSelected != -1) {
            allImages[previousSelected]!!.setSelected(false)
            previousSelected = ImagePosition
            indicatorRecycler!!.adapter!!.notifyDataSetChanged()
        } else {
            previousSelected = ImagePosition
        }
        imagePager!!.currentItem = ImagePosition
    }

    /**
     * the imageViewPager's adapter
     */
    inner class ImagesPagerAdapter() : PagerAdapter() {
        private lateinit var image: ImageView

        override fun getCount(): Int {
            return allImages.size
        }

        override fun instantiateItem(
            containerCollection: ViewGroup,
            position: Int
        ): Any {
            val layoutinflater = containerCollection.context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val view: View = layoutinflater.inflate(R.layout.picture_browser_pager, null)
            image = view.findViewById(R.id.image)
            setTransitionName(image, position.toString() + "picture")
            val pic: pictureFacer = allImages.get(position)!!
            Glide.with(animeContx!!)
                .load(pic.getPicturePath())
                .apply(RequestOptions().fitCenter())
                .into(image)

            mllabelbtn.setOnClickListener {
                val builder = AlertDialog.Builder(context)
                val dialogView = layoutInflater.inflate(R.layout.dialog_viewer, null)
                var dlbl = dialogView.findViewById<TextView>(R.id.d_photo_info)
                dlbl.setText("come on!")

                builder.setView(dialogView).create().show()
            }

//            mllabelbtn.setOnClickListener {
//
//                val labelImg = FirebaseVisionImage.fromBitmap(
//                    (image.drawable as BitmapDrawable).bitmap
//                )
//
//                val detector = FirebaseVision.getInstance().getOnDeviceImageLabeler()
//
//                detector.processImage(labelImg)
//                    .addOnSuccessListener { labels ->
//                        var output = "* "
//                        for (label in labels) {
//                            if (label.confidence > 0.7)
//                                output += label.text + " "
//                        }
//                        imgtag.text = output
//                    }
//                    .addOnFailureListener { e->
//                        // print mlcnt "i dont know"
//                    }
//            }

            image.setOnClickListener{
                    if (indicatorRecycler!!.getVisibility() == View.GONE) {
                        indicatorRecycler!!.setVisibility(View.VISIBLE)
                    } else {
                        indicatorRecycler!!.setVisibility(View.GONE)
                    }
                    /**
                     * uncomment the below condition and comment the one above to control recyclerView visibility automatically
                     * when image is clicked
                     */
            }
            (containerCollection as ViewPager).addView(view)
            return view
        }

        override fun destroyItem(
            containerCollection: ViewGroup,
            position: Int,
            view: Any
        ) {
            (containerCollection as ViewPager).removeView(view as View)
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view === `object` as View
        }
    }

    /**
     * function for controlling the visibility of the recyclerView indicator
     */
    private fun visibiling() {
        viewVisibilityController = 1
        val checker = viewVisibilitylooper
        Handler().postDelayed(Runnable {
            if (viewVisibilitylooper > checker) {
                visibiling()
            } else {
                indicatorRecycler!!.visibility = View.GONE
                viewVisibilityController = 0
                viewVisibilitylooper = 0
            }
        }, 4000)
    }
}