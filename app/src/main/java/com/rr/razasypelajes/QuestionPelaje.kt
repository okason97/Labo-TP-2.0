package com.rr.razasypelajes

import com.rr.razasypelajes.Horses.Horse

class QuestionPelaje : QuestionMode() {
    override fun getData(horse: Horse): String {
        return horse.pelaje
    }
}