package com.example.pulltorefresh

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var feedlist = ArrayList<Item>()
    private var addlist = ArrayList<Item>()
    private var cnt = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getdata()

        var linearManager = LinearLayoutManager(this)
        var adapter = RcvAdapter(feedlist)

        Rcview.layoutManager = linearManager
        Rcview.adapter = adapter

        swipelayout.viewTreeObserver.addOnScrollChangedListener {
            swipelayout.isEnabled = swipelayout.scrollY == 0
        }

        swipelayout.setOnRefreshListener {
            adapter.clear()
            if (cnt>=3)
                cnt = 0
            feedlist.add(0,addlist[cnt])
            cnt+=1
            adapter.addAll(feedlist)
            swipelayout.setRefreshing(false)
        }
    }

    fun getdata(){
        feedlist.add(Item(R.drawable.photo1,"heo_official"))
        feedlist.add(Item(R.drawable.photo2, "kotlin17"))
        feedlist.add(Item(R.drawable.photo3, "java_"))

        addlist.add(Item(R.drawable.feed1, "another_user"))
        addlist.add(Item(R.drawable.feed2, "heo_official"))
        addlist.add(Item(R.drawable.feed3, "kotlin20"))
    }


}
