package com.kinisi.trailtracker.ui.newactivity

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kinisi.trailtracker.MainActivity
import com.kinisi.trailtracker.R
import com.kinisi.trailtracker.models.DataModel
import com.kinisi.trailtracker.ui.newactivity.RecyclerAdapter

class RecyclerAdapter(
    private val dataModel: ArrayList<DataModel>
    ) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemTitle: TextView = itemView.findViewById(R.id.title)
        var itemType: TextView = itemView.findViewById(R.id.type)
        var itemDesc: TextView = itemView.findViewById(R.id.description)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.frame_trailview, viewGroup, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
       return dataModel.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as ViewHolder
        viewHolder.itemTitle.text = dataModel[position].title
        viewHolder.itemType.text = dataModel[position].dist.toString()
        viewHolder.itemDesc.text = dataModel[position].type
    }

}