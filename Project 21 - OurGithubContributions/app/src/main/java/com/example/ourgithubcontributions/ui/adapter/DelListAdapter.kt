package com.example.ourgithubcontributions.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.barryzhang.tcontributionsview.TContributionsView
import com.example.ourgithubcontributions.R
import com.example.ourgithubcontributions.data.model.User
import com.example.ourgithubcontributions.retrofit.RetrofitPresenter

class DelListAdapter (private val cbList: List<User>) : RecyclerView.Adapter<DelListAdapter.ViewHolder>(){

    override fun getItemCount(): Int = cbList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_del, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(cbList[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var item_name = itemView.findViewById<TextView>(R.id.del_username)
        var item_cbx = itemView.findViewById<CheckBox>(R.id.del_checkbox)

        fun bind(user: User){
            item_name.text = user.userName
        }
    }

}