package com.rr.razasypelajes

import android.widget.ImageView
import com.rr.razasypelajes.Helpers.JSONHelper
import com.rr.razasypelajes.Horses.Horse
import java.util.*

// interaccion_palabra_imagen
class InteraccionB(private val context : Game): GameMode(context){
    override fun newGame() {
        val horses = JSONHelper.fromJSON(Horse::class.java, context.resources.openRawResource(R.raw.horses))
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
        var id: Int
        for (i in  0 until chosenHorses.size) {
            id = context.resources.getIdentifier(chosenHorses[i].img, "drawable",
                    context.packageName)
            context.answerViews[i].findViewById<ImageView>(R.id.imageAnswer)
                    .setImageResource(id)
        }

        val answerIndex = r.nextInt(chosenHorses.size)

        questionMode.setText(context.findViewById(R.id.questionText), horses[answerIndex])

        context.answer = context.answerViews[answerIndex].id
    }
}