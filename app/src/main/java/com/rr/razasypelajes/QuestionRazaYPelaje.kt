package com.rr.razasypelajes

import com.rr.razasypelajes.horses.Horse

class QuestionRazaYPelaje : QuestionMode() {
    override fun getPrettyData(horse: Horse): String {
        return horse.prettyBoth() as String
    }

    override fun getData(horse: Horse): String {
        return "${horse.raza}_${horse.pelaje}"
    }

}
