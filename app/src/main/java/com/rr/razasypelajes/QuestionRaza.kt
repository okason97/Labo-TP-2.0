package com.rr.razasypelajes

import com.rr.razasypelajes.horses.Horse

class QuestionRaza : QuestionMode() {
    override fun getPrettyData(horse: Horse): String {
        return horse.raza.capitalize()
    }

    override fun getData(horse: Horse): String {
        return horse.raza
    }
}