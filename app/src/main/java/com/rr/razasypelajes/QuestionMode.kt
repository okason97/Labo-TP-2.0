package com.rr.razasypelajes

import android.widget.TextView
import com.rr.razasypelajes.Horses.Horse

abstract class QuestionMode {
    fun chooseHorses(horses: List<Horse>, size: Int): List<Horse>{
        var count = 0
        var chosenHorses : List<Horse> = ArrayList(size)
        var chosenRazasOPelajes : List<String> = ArrayList(size)
        for (i in 0 until size) {
            while (chosenRazasOPelajes.contains(getData(horses[count]))) {
                count++
            }
            chosenRazasOPelajes += getData(horses[count])
            chosenHorses += horses[count]
        }
        return chosenHorses
    }

    fun setText(view: TextView, horse: Horse){
        view.text = getData(horse)
    }

    abstract fun getData(horse: Horse): String
}