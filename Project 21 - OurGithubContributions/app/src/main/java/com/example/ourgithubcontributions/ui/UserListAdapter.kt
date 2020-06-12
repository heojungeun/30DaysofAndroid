package com.example.ourgithubcontributions.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.barryzhang.tcontributionsview.TContributionsView
import com.example.ourgithubcontributions.R
import com.example.ourgithubcontributions.data.model.User
import com.example.ourgithubcontributions.retrofit.RetrofitPresenter

class UserListAdapter(private val cbList: List<User>) : RecyclerView.Adapter<UserListAdapter.ViewHolder>(){

    //private var cbList: List<User> = listOf()

    override fun getItemCount(): Int = cbList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cb, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(cbList[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val item_name = itemView.findViewById<TextView>(R.id.eachName)
        private val item_cbview = itemView.findViewById<TContributionsView>(R.id.eachCbView)

        fun bind(user: User){
            item_name.text = user.userName
            RetrofitPresenter().getContributions(user.userName, item_cbview)
        }
    }

//    fun setList(newList: List<User>){
//        this.cbList = newList
//        notifyDataSetChanged()
//    }
}