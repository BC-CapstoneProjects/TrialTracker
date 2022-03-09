package com.kinisi.trailtracker.ui.favorites

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kinisi.trailtracker.R
import java.net.URL

class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

         var itemTitle: TextView = itemView.findViewById(R.id.title)
         var itemType: TextView = itemView.findViewById(R.id.type)
         var itemDesc: TextView = itemView.findViewById(R.id.description)

    }

    private val title1 = arrayOf("test",
        "test Park", "test's Way",
        "Red Rock Trail", "Andes Hike",
        "Golden Fountain Park", "Alki Beach")

    private val type1 = arrayOf("National Park",
        "Park", "Trail",
        "Trail", "Trail",
        "Park", "Beach")

    private val description1 = arrayOf("item 1",
        "item 2", "item 3",
        "item 4", "item 5",
        "item 6", "item 7")


    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.frame_trailview, viewGroup, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.itemTitle.text = title1[i]
        viewHolder.itemType.text = type1[i]
        viewHolder.itemDesc.text = description1[i]

    }

    override fun getItemCount(): Int {
        return title1.size
    }
}