package com.rr.razasypelajes

import com.rr.razasypelajes.Horses.Horse

class QuestionRaza : QuestionMode() {
    override fun getData(horse: Horse): String {
        return horse.raza
    }
}