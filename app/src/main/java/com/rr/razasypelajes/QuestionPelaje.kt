package com.rr.razasypelajes

import com.rr.razasypelajes.horses.Horse

class QuestionPelaje : QuestionMode() {
    override fun getPrettyData(horse: Horse): String {
        return horse.prettyPelaje() as String
    }

    override fun getData(horse: Horse): String {
        return horse.pelaje as String
    }
}