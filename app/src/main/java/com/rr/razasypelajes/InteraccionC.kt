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
        val chosenHorses : List<Horse>
        var horses = JSONHelper.fromJSON(Horse::class.java,
                context.resources.openRawResource(R.raw.horses))
        horses = horses.shuffled()
        chosenHorses = chooseHorses(horses, context.answerViews.size,
                cruzas[answerIndex].cruza as String)
        var id: Int
        for (i in  0 until chosenHorses.size) {
            id = context.resources.getIdentifier(chosenHorses[i].img, "drawable",
                    context.packageName)
            context.answerViews[i].findViewById<ImageView>(R.id.imageAnswer)
                    .setImageResource(id)
        }

        id = context.resources.getIdentifier(cruzas[answerIndex].img, "drawable",
                context.packageName)
        context.findViewById<ImageView>(R.id.questionImage)
                .setImageResource(id)

        context.answer = context.answerViews[
                chosenHorses.indexOfFirst {
                    horse -> horse.raza == cruzas[answerIndex].cruza
                }
        ].id
    }

    private fun chooseHorses(horses: List<Horse>, size: Int, cruza: String): List<Horse> {
        var found = false
        var chosenHorses : List<Horse> = ArrayList()
        var count = 0
        for (i in 0 until size-1){
            if (found){
                while (horses[count].raza == cruza) count++
                chosenHorses = chosenHorses + horses[count]
                count++
            }else{
                chosenHorses = chosenHorses + horses[count]
                if (horses[count].raza == cruza) found = true
                count++
            }
        }
        chosenHorses = chosenHorses + if (found){
            while (horses[count].raza == cruza)
                count++
            horses[count]
        }else{
            while (horses[count].raza != cruza)
                count++
            horses[count]
        }
        return chosenHorses.shuffled()
    }
}