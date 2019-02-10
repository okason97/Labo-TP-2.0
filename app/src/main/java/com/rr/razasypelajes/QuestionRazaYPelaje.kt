package com.rr.razasypelajes

import com.rr.razasypelajes.Horses.Horse

class QuestionRazaYPelaje : QuestionMode() {
    override fun getPrettyData(horse: Horse): String {
        return "${horse.raza} - ${horse.pelaje}"
    }

    override fun getData(horse: Horse): String {
        return "${horse.raza}_${horse.pelaje}"
    }

}
