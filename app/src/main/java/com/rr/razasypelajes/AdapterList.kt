package com.rr.razasypelajes

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.rr.razasypelajes.Horses.Horse

class AdapterList(private val context: Context,
                  private val dataSource: List<Horse>): BaseAdapter() {

    private val inflater: LayoutInflater
            = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return dataSource.size
    }

    override fun getItem(position: Int): Any {
        return dataSource[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val rowView = inflater.inflate(R.layout.recon_list_include, parent, false)

        val image = rowView.findViewById(R.id.listItemImage) as ImageView
        val text = rowView.findViewById(R.id.listItemText) as TextView
        val audio = rowView.findViewById(R.id.listItemAudio) as ImageButton
        val bigText = rowView.findViewById(R.id.listItemBigText) as TextView

        val horse = getItem(position) as Horse

        text.text = horse.raza
        // todo setear imagen y audio


        return rowView
    }
}