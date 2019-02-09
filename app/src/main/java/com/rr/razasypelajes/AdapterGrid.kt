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

class AdapterGrid(private val context: Context,
                  private val source: List<Horse>): BaseAdapter() {

    private val inflater: LayoutInflater
            = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    private val dataSource: Array<Array<Horse>> = initList()

    private fun initList(): Array<Array<Horse>> {
        val list: Array<Array<Horse>> = emptyArray()
        val cant = Math.round(source.size.toFloat() / 3)
        for (i in 0..cant) {
            val j = i*3
            list[i] = arrayOf(source[j], source[j+1], source[j+2])
        }
        return list
    }

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
        val rowView = inflater.inflate(R.layout.recon_grid_include, parent, false)

        val image = rowView.findViewById(R.id.gridItemImage) as ImageView
        val text = rowView.findViewById(R.id.gridItemText) as TextView
        val audio = rowView.findViewById(R.id.gridItemAudio) as ImageButton

        val horse = getItem(position) as Horse

        text.text = horse.raza
        // todo setear imagen y audio


        return rowView
    }
}