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

    private var horses: List<Horse> = context.horses
    private var sounds: List<MediaPlayer> = context.sounds

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.recon_grid_include, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return horses.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val horse = horses[position]
        holder.image.setImageResource(horse.getImageId(context))
        holder.audio.setOnClickListener { sounds[position].start() }
        holder.texto.text = horse.prettyRaza()
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView = itemView.findViewById(R.id.gridItemImage)
        var audio: ImageButton = itemView.findViewById(R.id.gridItemAudio)
        var texto: TextView = itemView.findViewById(R.id.gridItemText)
        var parentLayout: ConstraintLayout = itemView.findViewById(R.id.gridLayout)
    }
}