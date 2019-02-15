package com.rr.razasypelajes

import android.content.Context
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.widget.ImageView
import com.rr.razasypelajes.helpers.JSONHelper
import com.rr.razasypelajes.horses.Horse
import java.util.*

// interaccion_imagen_palabra
class InteraccionA(private val context : Game): GameMode(context){
    private val sounds : HashMap<String, MediaPlayer> = HashMap()


    override fun newGame() {
        var horses = JSONHelper.fromJSON(Horse::class.java,
                context.resources.openRawResource(R.raw.horses))
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
        for (i in 0 until chosenHorses.size) {
            questionMode.setText(context.answerViews[i].findViewById(R.id.textAnswer),
                    chosenHorses[i])
            questionMode.setSound(context.answerViews[i].findViewById(R.id.soundAnswer),
                    chosenHorses[i], context)
        }

        val answerIndex = r.nextInt(chosenHorses.size)

        val id = context.resources.getIdentifier(chosenHorses[answerIndex].img,
                "drawable", context.packageName)

        context.findViewById<ImageView>(R.id.questionImage)
                .setImageResource(id)

        context.answer = context.answerViews[answerIndex].id
    }
}