package com.rr.razasypelajes

import android.content.Context
import android.content.SharedPreferences
import android.widget.ImageView
import com.rr.razasypelajes.helpers.JSONHelper
import com.rr.razasypelajes.horses.Horse
import java.util.*

// interaccion_palabra_imagen
class InteraccionB(private val context : Game): GameMode(context){
    override fun newGame() {
        var horses = JSONHelper.fromJSON(Horse::class.java, context.resources.openRawResource(R.raw.horses))
        horses = horses.shuffled()
        val r = Random()
        val sharedPref : SharedPreferences = context.getSharedPreferences(
                context.getString(R.string.config), Context.MODE_PRIVATE);
        val razaOexclusivoPelaje = r.nextBoolean()
        val questionMode : QuestionMode
        questionMode = if (sharedPref.getInt(context.getString(R.string.minijuego), R.id.razasYPelajes) == R.id.razasYPelajes){
            if (razaOexclusivoPelaje){
                // raza
                QuestionRaza()
            }else{
                // pelaje
                QuestionPelaje()
            }
        }else{
            QuestionRazaYPelaje()
        }
        val chosenHorses : List<Horse>
        chosenHorses = questionMode.chooseHorses(horses, context.answerViews.size)
        var id: Int
        for (i in  0 until chosenHorses.size) {
            id = context.resources.getIdentifier(chosenHorses[i].img, "drawable",
                    context.packageName)
            context.answerViews[i].findViewById<ImageView>(R.id.imageAnswer)
                    .setImageResource(id)
        }

        val answerIndex = r.nextInt(chosenHorses.size)

        questionMode.setText(context.findViewById(R.id.questionText), horses[answerIndex])
        questionMode.setSound(context.findViewById(R.id.questionSound),
                horses[answerIndex], context)

        context.answer = context.answerViews[answerIndex].id
    }
}