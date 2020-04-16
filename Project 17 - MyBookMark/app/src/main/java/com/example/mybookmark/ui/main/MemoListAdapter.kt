package com.example.mybookmark.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mybookmark.R
import com.example.mybookmark.data.model.Memo

class MemoListAdapter(val memoItemClick: (Memo) -> Unit, val memoItemLongClick: (Memo) -> Unit) : RecyclerView.Adapter<MemoListAdapter.ViewHolder>(){

    private var memos: List<Memo> = listOf()

    override fun getItemCount(): Int = memos.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(memos[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        private val bname = itemView.findViewById<TextView>(R.id.itemTitle)
        private val btime = itemView.findViewById<TextView>(R.id.itemTime)
        private val bctnt = itemView.findViewById<TextView>(R.id.itemCtntView)
        private val bthumb = itemView.findViewById<TextView>(R.id.itemThumb)

        fun bind(memo : Memo){
            bname.text = memo.bookname
            btime.text = memo.time
            bctnt.text = memo.content
            //thumb 넣는거

            itemView.setOnClickListener {
                memoItemClick(memo)
            }

            itemView.setOnLongClickListener {
                memoItemLongClick(memo)
                true
            }
        }
    }

    fun setMemos(memos : List<Memo>){
        this.memos = memos
        notifyDataSetChanged()
    }

}