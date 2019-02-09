package com.rr.razasypelajes

import android.widget.ImageView
import android.widget.TextView
import com.rr.razasypelajes.Helpers.JSONHelper
import com.rr.razasypelajes.Horses.Horse
import java.util.*

class InteraccionA(private val questionType: Int) : GameMode() {
    override fun newGame(context: Game) {
        val horses = JSONHelper.fromJSON(Horse::class.java, context.resources.openRawResource(R.raw.horses))
        horses.shuffle()
        var id: Int
        for (i in  0..context.horsesViews.size) {
            id = context.resources.getIdentifier(horses[i].img, "drawable",
                    context.packageName)
            context.horsesViews[i].findViewById<ImageView>(R.id.imageAnswer)
                    .setImageResource(id)
        }

        val r = Random()
        val answerIndex = r.nextInt(context.horsesViews.size)

        if (questionType == R.id.razasYPelajes){
            if(r.nextBoolean()){
                (context.findViewById(R.id.questionText) as TextView)
                        .text = horses[answerIndex].raza
            }else{
                (context.findViewById(R.id.questionText) as TextView)
                        .text = horses[answerIndex].pelaje
            }
        }else{
            // razas y pelajes juntas
            (context.findViewById(R.id.questionText) as TextView)
                    .text = context.getString(R.string.razas_y_pelajes_pregunta,horses[answerIndex].raza,horses[answerIndex].pelaje)
        }
        context.answer = context.horsesViews[answerIndex]
                .findViewById<ImageView>(R.id.imageAnswer).id
    }
}