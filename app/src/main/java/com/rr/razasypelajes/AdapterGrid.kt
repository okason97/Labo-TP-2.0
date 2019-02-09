package com.rr.razasypelajes

import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.rr.razasypelajes.Horses.Horse

class AdapterGrid(context: Reconocimiento): RecyclerView.Adapter<AdapterGrid.ViewHolder>() {

    private var dataSource: List<Horse> = context.horses

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.recon_grid_include, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataSource.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val horse = dataSource[position]
        holder.texto.text = horse.raza
        TODO("Agregar imagen, sonido")
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView = itemView.findViewById(R.id.gridItemImage)
        var audio: ImageButton = itemView.findViewById(R.id.gridItemAudio)
        var texto: TextView = itemView.findViewById(R.id.gridItemText)
        var parentLayout: ConstraintLayout = itemView.findViewById(R.id.gridLayout)
    }
}