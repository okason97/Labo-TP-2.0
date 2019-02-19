package com.rr.razasypelajes

import android.content.Context
import android.media.MediaPlayer
import android.widget.Button
import android.widget.TextView
import com.rr.razasypelajes.horses.Horse

abstract class QuestionMode {
    fun chooseHorses(horses: List<Horse>, size: Int): List<Horse>{
        var count = 0
        var chosenHorses : List<Horse> = ArrayList(size)
        var chosenRazasOPelajes : List<String> = ArrayList(size)
        for (i in 0 until size) {
            while (chosenRazasOPelajes.contains(getData(horses[count]))) {
                println(getData(horses[count]))
                count++
            }
            println(getData(horses[count]))
            chosenRazasOPelajes += getData(horses[count])
            chosenHorses += horses[count]
            count++
        }
        return chosenHorses.shuffled()
    }

    fun setText(view : TextView, horse: Horse){
        view.text = getPrettyData(horse)
    }

    abstract fun getPrettyData(horse: Horse): String

    fun setSound(view : Button, horse : Horse, context : Context){
        view.setOnClickListener {
            try {
                val id = context.resources.getIdentifier("sound_"+getData(horse), "raw",
                        context.packageName)
                MediaPlayer.create(context, id).start()
            } catch (e: Exception) {
                MediaPlayer.create(context, R.raw.sound_resoplido).start()
            }
        }
    }

    abstract fun getData(horse: Horse): String
}
