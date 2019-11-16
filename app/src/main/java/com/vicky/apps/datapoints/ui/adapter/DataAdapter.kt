package com.vicky.apps.datapoints.ui.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.vicky.apps.datapoints.R
import com.vicky.apps.datapoints.ui.model.SongsListResponse


class DataAdapter constructor(var data:List<SongsListResponse>, val clickListener: (Int)-> Unit) : RecyclerView.Adapter<DataAdapter.DataViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.recycler_child_view,parent,false)
        return DataViewHolder(v)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.songName.text = data[position].song
        Picasso.get().load(data[position].coverImage).into(holder.imageView)
        holder.imageView.setOnClickListener { clickListener(position) }
    }

    fun updateData(data: List<SongsListResponse>){
        this.data = data
        notifyDataSetChanged()
    }
    class DataViewHolder(v:View): RecyclerView.ViewHolder(v){
        var imageView = v.findViewById<ImageView>(R.id.coverPic)
        var songName = v.findViewById<TextView>(R.id.songName)
    }
}