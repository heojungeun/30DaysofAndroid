package com.example.mybookmark.ui.mybooktower

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mybookmark.R
import com.example.mybookmark.data.model.Memo
import kotlinx.android.synthetic.main.list_book.view.*
import java.util.Random

class BookListAdapter() : RecyclerView.Adapter<BookListAdapter.ViewHolder>() {

    private var memos: List<Memo> = listOf()

    override fun getItemCount(): Int{
        return memos.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookListAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_book, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookListAdapter.ViewHolder, position: Int) {
        holder.bind(memos[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        fun bind(memo:Memo){
            val random = Random()
            val num = random.nextInt(3)
            if(num==1){
                itemView.setBackgroundResource(R.color.colorBook1)
            }else if(num==2){
                itemView.setBackgroundResource(R.color.colorBook2)
            }else{
                itemView.setBackgroundResource(R.color.colorBook3)
            }
            itemView.tower_book_name.text = memo.bookname
        }
    }

    fun setMemos(memos : List<Memo>){
        this.memos = memos
        notifyDataSetChanged()
    }
}