package com.rr.razasypelajes

import android.widget.ImageView
import com.rr.razasypelajes.helpers.JSONHelper
import com.rr.razasypelajes.horses.Horse
import com.rr.razasypelajes.horses.Padres
import java.util.*
import kotlin.collections.ArrayList

// interaccion_imagen_imagen
class InteraccionC(private val context : Game): GameMode(context) {
    override fun newGame() {
        val r = Random()
        val cruzas = JSONHelper.fromJSON(Padres::class.java,
                context.resources.openRawResource(R.raw.padres))
        val answerIndex = r.nextInt(cruzas.size)
        var chosenHorses : List<Int>
        var horses = JSONHelper.fromJSON(Horse::class.java,
                context.resources.openRawResource(R.raw.horses))
        horses = horses.shuffled()
        val childImageId = cruzas[answerIndex].getImageId(context)
        chosenHorses = chooseHorses(horses, context.answerViews.size) + childImageId
        chosenHorses = chosenHorses.shuffled()
        for (i in  0 until chosenHorses.size) {
            context.answerViews[i].findViewById<ImageView>(R.id.imageAnswer)
                    .setImageResource(chosenHorses[i])
        }

        val parentImgs = cruzas[answerIndex].getParentsImagesIds(context)

        context.findViewById<ImageView>(R.id.questionImage1)
                .setImageResource(parentImgs[0])
        context.findViewById<ImageView>(R.id.questionImage2)
                .setImageResource(parentImgs[1])

        context.answer = context.answerViews[
                chosenHorses.indexOfFirst {
                    id -> id == childImageId
                }
        ].id
    }

    private fun chooseHorses(horses: List<Horse>, size: Int): List<Int> {
        var chosenHorses : List<Int> = ArrayList()
        for (i in 0 until size-1){
            chosenHorses += horses[i].getImageId(context)
        }
        return chosenHorses
    }
}