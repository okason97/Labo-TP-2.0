package com.rr.razasypelajes

import android.media.MediaPlayer
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.rr.razasypelajes.horses.AbsHorse
import com.rr.razasypelajes.horses.Horse

class AdapterList(val context: Reconocimiento): RecyclerView.Adapter<AdapterList.ViewHolder>() {

    private val horses: List<AbsHorse> = context.horses
    private val sounds: List<MediaPlayer> = context.sounds

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.recon_list_include, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return horses.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val horse = horses[position]
        holder.image.setImageResource(horse.getImageId(context))
        holder.audio.setOnClickListener { sounds[position].start() }
        holder.texto.text = horse.getReconText()
        holder.bigText.text = horse.text
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.listItemImage)
        val audio: ImageButton = itemView.findViewById(R.id.listItemAudio)
        val texto: TextView = itemView.findViewById(R.id.listItemText)
        val bigText: TextView = itemView.findViewById(R.id.listItemBigText)
    }
}