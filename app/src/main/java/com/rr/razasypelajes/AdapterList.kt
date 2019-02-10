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

class AdapterList(context: Reconocimiento): RecyclerView.Adapter<AdapterList.ViewHolder>() {

    private var dataSource: List<Horse> = context.horses

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.recon_list_include, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataSource.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val horse = dataSource[position]
        holder.texto.text = horse.raza
        TODO("Agregar imagen, sonido y texto grande")
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView = itemView.findViewById(R.id.listItemImage)
        var audio: ImageButton = itemView.findViewById(R.id.listItemAudio)
        var texto: TextView = itemView.findViewById(R.id.listItemText)
        var bigText: TextView = itemView.findViewById(R.id.listItemBigText)
        var parentLayout: ConstraintLayout = itemView.findViewById(R.id.listLayout)
    }
}