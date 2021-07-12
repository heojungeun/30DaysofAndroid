package com.example.project25_danggun.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project25_danggun.R
import com.example.project25_danggun.databinding.FragmentHomeBinding


class HomeFragment: Fragment(R.layout.fragment_home)  {

    private var binding: FragmentHomeBinding? = null
    private lateinit var articleAdpater: ArticleAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentHomeBinding = FragmentHomeBinding.bind(view)
        binding = fragmentHomeBinding

        articleAdpater = ArticleAdapter()

        fragmentHomeBinding.articleRecyclerView.layoutManager = LinearLayoutManager(context)
        fragmentHomeBinding.articleRecyclerView.adapter =
    }
}