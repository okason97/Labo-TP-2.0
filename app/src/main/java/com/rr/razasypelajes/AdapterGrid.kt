package com.rr.razasypelajes

import android.media.MediaPlayer
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.rr.razasypelajes.horses.Horse

class AdapterGrid(var context: Reconocimiento): RecyclerView.Adapter<AdapterGrid.ViewHolder>() {

    private var dataSource: List<Horse> = context.horses
    private var sound = MediaPlayer.create(context, R.raw.horse_sound) as MediaPlayer

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.recon_grid_include, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataSource.size
    }

    // TODO("Agregar imagen, sonido")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val horse = dataSource[position]
        holder.image.setImageResource(R.drawable.horse1)
        holder.audio.setOnClickListener { sound.start() }
        holder.texto.text = horse.raza
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView = itemView.findViewById(R.id.gridItemImage)
        var audio: ImageButton = itemView.findViewById(R.id.gridItemAudio)
        var texto: TextView = itemView.findViewById(R.id.gridItemText)
        var parentLayout: ConstraintLayout = itemView.findViewById(R.id.gridLayout)
    }
}