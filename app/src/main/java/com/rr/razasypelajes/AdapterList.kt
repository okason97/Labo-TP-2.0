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
import com.rr.razasypelajes.Horses.Horse

class AdapterList(var context: Reconocimiento): RecyclerView.Adapter<AdapterList.ViewHolder>() {

    private var dataSource: List<Horse> = context.horses
    private var sound = MediaPlayer.create(context, R.raw.horse_sound) as MediaPlayer

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.recon_list_include, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataSource.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val horse = dataSource[position]
        holder.image.setImageResource(R.drawable.horse1)
        holder.audio.setOnClickListener { sound.start() }
        holder.texto.text = horse.raza
        holder.bigText.text = context.resources.getText(R.string.loreipsum)
        // TODO("Agregar imagen, sonido y texto grande, los que estan son placeholders")
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView = itemView.findViewById(R.id.listItemImage)
        var audio: ImageButton = itemView.findViewById(R.id.listItemAudio)
        var texto: TextView = itemView.findViewById(R.id.listItemText)
        var bigText: TextView = itemView.findViewById(R.id.listItemBigText)
        var parentLayout: ConstraintLayout = itemView.findViewById(R.id.listLayout)
    }
}