package com.rr.razasypelajes

import android.widget.ImageView
import android.widget.TextView
import com.rr.razasypelajes.Helpers.JSONHelper
import com.rr.razasypelajes.Horses.Horse
import java.util.*

// interaccion_imagen_palabra
class InteraccionA(private val context : Game): GameMode(context){
    override fun newGame() {
        val horses = JSONHelper.fromJSON(Horse::class.java,
                context.resources.openRawResource(R.raw.horses))
        horses.shuffle()
        val r = Random()
        val razaOexclusivoPelaje = r.nextBoolean()
        val questionMode : QuestionMode
        if (razaOexclusivoPelaje){
            // raza
            questionMode = QuestionRaza()
        }else{
            // pelaje
            questionMode = QuestionPelaje()
        }
        val chosenHorses : List<Horse>
        chosenHorses = questionMode.chooseHorses(horses, context.answerViews.size)
        for (i in  0 until chosenHorses.size) {
            questionMode.setText(context.answerViews[i].findViewById<TextView>(R.id.textAnswer),
                    chosenHorses[i])
        }

        val answerIndex = r.nextInt(chosenHorses.size)

        val id = context.resources.getIdentifier(chosenHorses[answerIndex].img,
                "drawable", context.packageName)
        context.findViewById<ImageView>(R.id.questionImage)
                .setImageResource(id)

        context.answer = context.answerViews[answerIndex].id
    }
}