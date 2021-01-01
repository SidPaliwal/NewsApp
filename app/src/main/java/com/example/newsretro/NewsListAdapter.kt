package com.example.newsretro

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class NewsListAdapter(val context:Context,val items:List<News>,private val listener:newsitemclicked): RecyclerView.Adapter<NewsListAdapter.newsviewholder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): newsviewholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.news_item,parent,false)
        val vew = newsviewholder(view)
        view.setOnClickListener {
            listener.onitemselected(items[vew.adapterPosition])
        }
        return vew
    }

    override fun onBindViewHolder(holder: newsviewholder, position: Int) {
        holder.titletext.text = items[position].title
        holder.authortext.text = items[position].author
        holder.news_description.text = items[position].description
        Glide.with(context).load(items[position].urlToImage).into(holder.newsimage)

    }

    override fun getItemCount(): Int {
            return items.size
    }
    class newsviewholder(itemView: View) : RecyclerView.ViewHolder(itemView){

        var titletext = itemView.findViewById<TextView>(R.id.news_title)
        var authortext = itemView.findViewById<TextView>(R.id.news_author)
        var newsimage = itemView.findViewById<ImageView>(R.id.news_image)
        var news_description = itemView.findViewById<TextView>(R.id.news_description)

    }
}

interface newsitemclicked{

    fun onitemselected(item:News)

}