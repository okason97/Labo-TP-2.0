package com.rr.razasypelajes

import com.rr.razasypelajes.Horses.Horse

class QuestionPelaje : QuestionMode() {
    override fun getPrettyData(horse: Horse): String {
        return horse.pelaje.capitalize()
    }

    override fun getData(horse: Horse): String {
        return horse.pelaje
    }
}