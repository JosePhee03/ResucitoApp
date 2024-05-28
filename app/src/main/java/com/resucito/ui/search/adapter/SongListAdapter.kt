package com.resucito.ui.search.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.resucito.R
import com.resucito.model.SongList

class SongListAdapter(private val dataList: List<SongList>) :
    RecyclerView.Adapter<SongListAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleItem: TextView = itemView.findViewById(R.id.titleItem)
        val subtitleItem: TextView = itemView.findViewById(R.id.subtitleItem)
        val pageItem: TextView = itemView.findViewById(R.id.pageItem)
        val favoriteButtonItem: ImageButton = itemView.findViewById(R.id.favoriteButtonItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.song_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.titleItem.text = dataList[position].title
        holder.subtitleItem.text = dataList[position].subtitle
        holder.pageItem.text = dataList[position].page.toString()
        if (dataList[position].favorite) {
            holder.favoriteButtonItem.setImageResource(R.drawable.ic_favorite_on)
        } else {
            holder.favoriteButtonItem.setImageResource(R.drawable.ic_favorite_off)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}